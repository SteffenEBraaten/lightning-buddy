package com.example.in2000_project.maps

import android.content.res.Resources
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.SeekBar
import com.example.in2000_project.R

class RadiusFragment: Fragment() {
    private lateinit var fragment: View
    private lateinit var min: Integer
    private lateinit var max: Integer
    private lateinit var buttonText: String


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragment = inflater.inflate(R.layout.set_radius_fragment, container, false)
        val argumentsBundle: Bundle? = arguments

        val editText: EditText = fragment.findViewById(R.id.radius_input_field)
        val seekbar: SeekBar = fragment.findViewById(R.id.radius_seek)
        parseArguments(argumentsBundle, seekbar, editText)
        setEditTextValue(editText, seekbar)
        setSeekBarValue(editText, seekbar)
        return fragment
    }
    private fun parseArguments(arguments: Bundle?, seekbar: SeekBar, editText: EditText) {
        try {
            val min = arguments?.getString("min")
            val max = arguments?.getString("max")
            val buttonText = arguments?.getString("buttonText")
            seekbar.max = Integer.parseInt(max) + Integer.parseInt(min)
            seekbar.progress = Integer.parseInt(min)
            fragment.findViewById<Button>(R.id.save_button).text = buttonText



        } catch (e: Resources.NotFoundException) {
            Log.e("Radius Fragment", "Could not find argument $e")
        }
    }
    private fun setSeekBarValue(editText: EditText,seekbar: SeekBar) {
        editText.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (editText.text.toString() != ""){
                    seekbar.progress = Integer.parseInt(editText.text.toString())
                    editText.setSelection(editText.text.length)
                }

            }

        })
    }

    private fun setEditTextValue(editText: EditText, seekbar: SeekBar) {
        editText.setText(seekbar.progress.toString())
        seekbar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                editText.setText(progress.toString())
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })
    }
}