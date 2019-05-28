package com.numberpuzzle.numberpuzzle

import android.content.Context;
import android.os.Bundle
import android.os.SystemClock
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import android.widget.*
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {

    var gridView: GridView? = null

    //var imageIDsAnswer = arrayOf<Int>(R.drawable.one, R.drawable.two, R.drawable.three, R.drawable.four, R.drawable.five, R.drawable.six, R.drawable.seven, R.drawable.eight, R.drawable.blank)
    var imageIDs = ArrayList<Int>()
    var firstClick = 0
    var secondClick = 0
    var positionClick1 = 0
    var positionClick2 = 0
    var statusClick1 = false
    var win = false
    var offset: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnreset = findViewById<Button>(R.id.btn_reset)
        val chronometer = findViewById<Chronometer>(R.id.chronometer)


        //Add Default
        imageIDs.add(R.drawable.one)
        imageIDs.add(R.drawable.two)
        imageIDs.add(R.drawable.three)
        imageIDs.add(R.drawable.four)
        imageIDs.add(R.drawable.five)
        imageIDs.add(R.drawable.six)
        imageIDs.add(R.drawable.seven)
        imageIDs.add(R.drawable.eight)
        imageIDs.add(R.drawable.blank)



//        imageIDs.toMutableList().shuffle()

        gridView = findViewById(R.id.gridview)
        //gridView?.setAdapter(ImageAdapterGridView(this))
        gridView?.adapter = ImageAdapterGridView(this)
        //Shuffle
        Collections.shuffle(imageIDs)
        chronometer.start()
        gridView?.setOnItemClickListener { parent, view, position, id ->
            if(!statusClick1 && !win)
            {
                //First Click
                statusClick1 = true
                positionClick1 = position
                firstClick = imageIDs[position]
            }
            else if(statusClick1)
            {
                //Second Click
                secondClick = imageIDs[position]
                positionClick2 = position

                //Check Blank
                if(secondClick == R.drawable.blank)
                {
                    //Toast.makeText(this,"Yes Blank",Toast.LENGTH_SHORT).show()
                    var check = 0
                    if(positionClick1>positionClick2)
                    {
                        check = positionClick1-positionClick2
                    }
                    else
                    {
                        check = positionClick2-positionClick1
                    }
//                    Toast.makeText(this,"Check : "+check,Toast.LENGTH_SHORT).show()

                    //Check No tayangq
                    if(check==1||check==3) {
                        imageIDs[positionClick1] = R.drawable.blank
                        imageIDs[positionClick2] = firstClick
                        gridView?.invalidateViews()
                        if(imageIDs[0] == R.drawable.one && imageIDs[1] == R.drawable.two && imageIDs[2] == R.drawable.three && imageIDs[3] == R.drawable.four && imageIDs[4] == R.drawable.five && imageIDs[5] == R.drawable.six && imageIDs[6] == R.drawable.seven && imageIDs[7] == R.drawable.eight && imageIDs[8] == R.drawable.blank) {
                            chronometer.stop()
                            win = true
                            Toast.makeText(this,"Complete",Toast.LENGTH_SHORT).show()
                        }
                    }
                }

                statusClick1 = false
            }

            //Toast.makeText(this,"Click : "+imageIDs[position],Toast.LENGTH_SHORT).show()
        }

        btnreset.setOnClickListener {
            chronometer.setBase(SystemClock.elapsedRealtime())
            offset = 0
            chronometer.start()
            gridView?.adapter = ImageAdapterGridView(this)
            Collections.shuffle(imageIDs)
            win = false
            statusClick1 = false
        }

    }

    inner class ImageAdapterGridView(private val mContext: Context) : BaseAdapter() {

        override fun getCount(): Int {
            return imageIDs.size
        }

        override fun getItem(position: Int): Any? {
            return null
        }

        override fun getItemId(position: Int): Long {
            return 0
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val mImageView: ImageView

  //          if (convertView == null) {
                mImageView = ImageView(mContext)
                mImageView.scaleType = ImageView.ScaleType.CENTER_CROP
                mImageView.setPadding(16, 16, 16, 16)
  //          }
//            } else {
//                mImageView = convertView as ImageView?
//            }
            mImageView.setImageResource(imageIDs[position])
            return mImageView
        }
    }
}
