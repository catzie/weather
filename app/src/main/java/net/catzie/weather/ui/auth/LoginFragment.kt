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
import net.catzie.weather.databinding.FragmentLoginBinding
import net.catzie.weather.model.ApiResult
import net.catzie.weather.model.auth.FakeAuthResponse
import net.catzie.weather.ui.main.MainActivity

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    private val viewModel: AuthViewModel by activityViewModels { AuthViewModel.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpListeners()
        setUpObservers()
    }

    private fun setUpListeners() {
        binding.btnLogin.setOnClickListener {
            viewModel.onClickLogin(
                binding.etUsername.text.toString(),
                binding.etPassword.text.toString(),
            )
        }

        binding.btnRegisterFromLogin.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registrationFragment)
        }
    }

    private fun setUpObservers() {
        viewModel.loginResult.observe(viewLifecycleOwner, ::displayLoginResult)
    }

    private fun displayLoginResult(apiResult: ApiResult<FakeAuthResponse>) {

        when (apiResult) {

            is ApiResult.Error -> {

                // Display error message
                val toastMessage = "Error: " + getString(apiResult.errorResId)
                Toast.makeText(context, toastMessage, Toast.LENGTH_LONG).show()
            }

            is ApiResult.Success -> {

                // Display success message
                val toastMessage = getString(R.string.login_res_success)
                Toast.makeText(context, toastMessage, Toast.LENGTH_LONG).show()

                // Launch MainActivity
                val intent = Intent(activity, MainActivity::class.java)
                activity?.startActivity(intent)

                // Exit AuthActivity
                activity?.finish()
            }
        }
    }

}