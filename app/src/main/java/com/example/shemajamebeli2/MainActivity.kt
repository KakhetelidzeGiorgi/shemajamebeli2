package com.example.shemajamebeli2

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Color.green
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.text.isDigitsOnly
import com.example.shemajamebeli2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var activeUser = 0
    private var deletedUser = 0
    private val data = mutableListOf<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setListeners()
    }

    private fun setListeners() {
        binding.addUserBtn.setOnClickListener {
            if (checkValidityOfInputs()) {
                addUser()
            }
        }

        binding.removeUserBtn.setOnClickListener {
            if (checkValidityOfInputs()) {
                removeUser()
            }
        }

        binding.updateUserBtn.setOnClickListener {
            if (checkValidityOfInputs()) {
                update()
            }
        }
    }

    private fun update() {
        val user = User(
            firstName = binding.firstNameEditTxtView.text.toString(),
            lastName = binding.lastNameNameEditTxtView.text.toString(),
            age = binding.ageEditTxtView.text.toString().toInt(),
            email = binding.emailEditTxtView.text.toString()
        )

        val curMail = binding.emailEditTxtView.text.toString()

        for((index,element) in data.withIndex()){
            if(element.email == curMail){
                data[index].age = binding.ageEditTxtView.text.toString().toInt()
                data[index].firstName = binding.firstNameEditTxtView.text.toString()
                data[index].lastName = binding.lastNameNameEditTxtView.text.toString()
                setSuccess()
                return
            }
        }
        setError()
    }

    private fun addUser() {
        val user = User(
            firstName = binding.firstNameEditTxtView.text.toString(),
            lastName = binding.lastNameNameEditTxtView.text.toString(),
            age = binding.ageEditTxtView.text.toString().toInt(),
            email = binding.emailEditTxtView.text.toString()
        )
        if (data.contains(user)) {
            setError()
            toast(USER_EXIST)
        } else {
            data.add(user)
            activeUser++
            setSuccess()
            toast(USER_ADDED)
        }
        updateUsersCount()
    }

    private fun updateUsersCount() {
        binding.removedUsersTxtView.text = deletedUser.toString()
        binding.activeMembersTxtView.text = activeUser.toString()
    }

    private fun removeUser() {
        val user = User(
            firstName = binding.firstNameEditTxtView.text.toString(),
            lastName = binding.lastNameNameEditTxtView.text.toString(),
            age = binding.ageEditTxtView.text.toString().toInt(),
            email = binding.emailEditTxtView.text.toString()
        )

        if (data.contains(user)) {
            data.remove(user)
            deletedUser++
            activeUser--
            binding.errorSuccessTxtView.text = SUCCESS
            binding.errorSuccessTxtView.setTextColor((Color.GREEN))
            toast(REMOVED_USER_SUCCESS)
        } else {
            toast(USER_NOT_EXIST)
            setError()
        }

        updateUsersCount()
    }

    private fun setError() {
        binding.errorSuccessTxtView.text = ERROR
        binding.errorSuccessTxtView.setTextColor(Color.RED)
    }

    private fun setSuccess() {
        binding.errorSuccessTxtView.text = SUCCESS
        binding.errorSuccessTxtView.setTextColor((Color.GREEN))
    }

    private fun checkValidityOfInputs(): Boolean {
        when {
            binding.firstNameEditTxtView.text.isNullOrEmpty() ||
                    binding.lastNameNameEditTxtView.text.isNullOrEmpty() ||
                    binding.ageEditTxtView.text.isNullOrEmpty() -> {
                toast(
                    FILL_FIELDS
                )
                return false
            }
            !binding.emailEditTxtView.text.toString().checkEmail() -> {
                toast(EMAIL_VALID)
                return false
            }
            !binding.ageEditTxtView.text.toString().isDigitsOnly() -> {
                toast(AGE_VALID)
                return false
            }
            else -> {
                return true
            }
        }
    }

    private fun toast(text: String) = Toast.makeText(this, text, Toast.LENGTH_SHORT).show()

    private fun String.checkEmail(): Boolean =
        android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()

    companion object {
        const val EMAIL_VALID = "Email is not validate"
        const val REMOVED_USER_SUCCESS = "User Removed Successfully"
        const val USER_EXIST = "User already exists"
        const val USER_NOT_EXIST = "User does not exists"
        const val USER_ADDED = "User added successfully"
        const val AGE_VALID = "Age is not integer"
        const val FILL_FIELDS = "You must fill every field"
        const val SUCCESS = "Success"
        const val ERROR = "Success"
    }

}