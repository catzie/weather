package net.catzie.weather.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import net.catzie.weather.R
import net.catzie.weather.databinding.FragmentRegistrationBinding
import net.catzie.weather.model.ApiResult
import net.catzie.weather.model.auth.FakeAuthResponse
import net.catzie.weather.ui.main.MainActivity
import timber.log.Timber

class RegistrationFragment : Fragment() {

    private lateinit var binding: FragmentRegistrationBinding

    private val viewModel: AuthViewModel by activityViewModels { AuthViewModel.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpListeners()
        setUpObservers()
    }

    private fun setUpListeners() {
        binding.btnLoginFromRegister.setOnClickListener {
            findNavController().navigate(R.id.action_registrationFragment_to_loginFragment2)
        }

        binding.btnRegister.setOnClickListener {
            viewModel.onClickRegister(
                binding.etUsername.text.toString(),
                binding.etPassword.text.toString(),
                binding.etFirstname.text.toString(),
                binding.etLastname.text.toString(),
            )
        }
    }

    private fun setUpObservers() {
        viewModel.registrationResult.observe(viewLifecycleOwner, ::displayRegistrationResult)
    }

    private fun displayRegistrationResult(apiResult: ApiResult<FakeAuthResponse>) {
        when (apiResult) {

            is ApiResult.Error -> {

                // Display failure message
                val toastMessage = "Error: " + getString(apiResult.errorResId)
                Toast.makeText(context, toastMessage, Toast.LENGTH_LONG).show()
            }

            is ApiResult.Success -> {

                Timber.d(getString(R.string.registration_res_success))

                // Launch MainActivity
                val intent = Intent(activity, MainActivity::class.java)
                activity?.startActivity(intent)

                // Exit AuthActivity
                activity?.finish()
            }
        }
    }
}