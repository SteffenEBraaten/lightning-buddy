package com.example.in2000_project.maps

import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.FocusFinder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.in2000_project.BaseActivity
import com.example.in2000_project.R

class RadiusFragment: Fragment() {
    private lateinit var fragment: View
    private var min: String? = null
    private var max: String? = null
    private var buttonText: String? = null

    internal lateinit var callback: OnRadiusChangeListener

    fun setOnRadiusChangeListener(callback: OnRadiusChangeListener) {
        this.callback = callback
    }
    interface OnRadiusChangeListener {
        fun onRadiusChanged(radius: Int)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragment = inflater.inflate(R.layout.set_radius_fragment, container, false)
        val argumentsBundle: Bundle? = arguments
        val editText: EditText = fragment.findViewById(R.id.radius_input_field)
        val seekbar: SeekBar = fragment.findViewById(R.id.radius_seek)
        parseArguments(argumentsBundle, seekbar, editText)
        setEditTextValue(editText, seekbar)
        setSeekBarValue(editText, seekbar)
        setButtonlListener()
        return fragment
    }

    private fun setButtonlListener() {
        val button = fragment.findViewById<Button>(R.id.save_button)
        if (buttonText.equals("Save")) {
            button.setOnClickListener {
                //TODO: Handle save click
            }
        } else if (buttonText.equals("Set")) {
            button.setOnClickListener {
                //TODO: Handle set click
            }
        } else {
            Log.e("Radius fragment", "Button text argument not recognized")
        }
    }

    private fun parseArguments(arguments: Bundle?, seekbar: SeekBar, editText: EditText) {
        try {
            val measure = arguments?.getString("measure")
            fragment.findViewById<TextView>(R.id.measure).text = measure

            max = arguments?.getString("max")
            seekbar.max = Integer.parseInt(max)

            min = arguments?.getString("min")
            seekbar.progress = Integer.parseInt(min)

            buttonText = arguments?.getString("buttonText")
            fragment.findViewById<Button>(R.id.save_button).text = buttonText

            val textBody = arguments?.getString("bodyText")
            fragment.findViewById<TextView>(R.id.radius_text).text = textBody
        } catch (e: Resources.NotFoundException) {
            Log.e("Radius Fragment", "Could not find argument $e")
        }
    }
    private fun setSeekBarValue(editText: EditText, seekbar: SeekBar) {
        editText.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (editText.text.toString() != "") {
                    if (Integer.parseInt(editText.text.toString()) < 10) {
                        editText.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
                            Toast.makeText(activity,"Radius can not be less than $min", Toast.LENGTH_SHORT).show()
                            seekbar.progress = Integer.parseInt(editText.text.toString())
                            editText.setSelection(editText.text.length)
                        }
                    } else {
                        seekbar.progress = Integer.parseInt(editText.text.toString())
                        editText.setSelection(editText.text.length)
                        callback.onRadiusChanged(Integer.parseInt(editText.text.toString()))
                    }
                }

            }

        })
    }

    private fun setEditTextValue(editText: EditText, seekbar: SeekBar) {
        editText.setText(seekbar.progress.toString())
        seekbar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val minInt: Int = Integer.parseInt(min)
                if (progress < minInt) {
                    seekBar?.progress = minInt
                } else {
                    editText.setText(progress.toString())
                }
                callback.onRadiusChanged(Integer.parseInt(editText.text.toString()))
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })
    }
}