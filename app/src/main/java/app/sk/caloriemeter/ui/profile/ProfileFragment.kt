package app.sk.caloriemeter.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import app.sk.caloriemeter.databinding.FragmentProfileBinding
import app.sk.caloriemeter.db.AppDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var appDb: AppDatabase

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(ProfileViewModel::class.java)

        appDb = AppDatabase.getDatabase(requireContext())

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        GlobalScope.launch {
            var user = appDb.userDao().getUser()
            binding.tvName.setText(user.firstName)
            binding.tvWeight.setText(user.weight)
            binding.tvHeight.setText(user.height)
            binding.tvAge.setText(user.age)
            binding.tvCalo.setText(user.dailyTarget.toString())

        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}