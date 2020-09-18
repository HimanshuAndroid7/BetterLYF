package com.betterlyf.app.helper

import android.R
import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.TextView


@SuppressLint("AppCompatCustomView")
class ExpandableTextView : TextView, View.OnClickListener {
    /* Custom method because standard getMaxLines() requires API > 16 */
    var myMaxLines = Int.MAX_VALUE
        private set

    constructor(context: Context?) : super(context) {
        setOnClickListener(this)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        setOnClickListener(this)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        setOnClickListener(this)
    }

    override fun onTextChanged(
        text: CharSequence,
        start: Int,
        lengthBefore: Int,
        lengthAfter: Int
    ) {
        /* If text longer than MAX_LINES set DrawableBottom - I'm using '...' icon */
        post {
            if (lineCount > MAX_LINES) setCompoundDrawablesWithIntrinsicBounds(
                0,
                0,
                0,
                R.drawable.btn_dropdown
            ) else setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
            maxLines = MAX_LINES
        }
    }

    override fun setMaxLines(maxLines: Int) {
        myMaxLines = maxLines
        super.setMaxLines(maxLines)
    }

    override fun onClick(v: View?) {
        /* Toggle between expanded collapsed states */
        maxLines = if (myMaxLines == Int.MAX_VALUE) MAX_LINES else Int.MAX_VALUE
    }

    companion object {
        private const val MAX_LINES = 5
    }
}