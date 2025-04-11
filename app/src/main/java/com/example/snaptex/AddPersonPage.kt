import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.snaptex.R
import com.example.snaptex.RVAdapterData
import com.example.snaptex.UsersData
import com.example.snaptex.UsersRVAdapter
import com.example.snaptex.databinding.FragmentAddPersonPageBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AddPersonPage : Fragment() {

    private lateinit var binding: FragmentAddPersonPageBinding
    private lateinit var adapter: UsersRVAdapter
    private lateinit var dataList: ArrayList<RVAdapterData>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentAddPersonPageBinding.inflate(inflater, container, false)

        dataList = ArrayList()
        adapter = UsersRVAdapter(requireContext(), dataList)
        binding.rv.layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        binding.rv.adapter = adapter
        binding.title2.visibility = View.VISIBLE

        val database = FirebaseDatabase.getInstance()
        val users = database.getReference("Users")

        binding.search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val queryText = s.toString().trim()
                if (queryText.isNotEmpty()) {
                    searchUsers(queryText, users)
                    binding.title2.visibility = View.GONE
                } else {
                    dataList.clear()
                    adapter.notifyDataSetChanged()
                    binding.title2.visibility = View.VISIBLE
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        return binding.root
    }

    private fun searchUsers(query: String, usersRef: DatabaseReference) {
        val searchQuery = usersRef.orderByChild("username")
            .startAt(query)
            .endAt(query + "\uf8ff").limitToFirst(3)

        searchQuery.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                dataList.clear()
                for (p in snapshot.children) {
                    val user = p.getValue(UsersData::class.java)
                    if (user != null) {
                        dataList.add(RVAdapterData(user.mail ?: "", user.username ?: "", user.password ?: "", R.drawable.logo))
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
}
