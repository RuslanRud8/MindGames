package com.developer.rrd_projects.mindgames.person

import com.developer.rrd_projects.mindgames.R

var icons:ArrayList<Int> = ArrayList()

fun loadImages() {
     icons.add(R.drawable.icon_0)
     icons.add(R.drawable.icon_1)
     icons.add(R.drawable.icon_2)
     icons.add(R.drawable.icon_3)
     icons.add(R.drawable.icon_4)
     icons.add(R.drawable.icon_5)
     icons.add(R.drawable.icon_21)
     icons.add(R.drawable.icon_7)
     icons.add(R.drawable.icon_8)
     icons.add(R.drawable.icon_9)
     icons.add(R.drawable.icon_10)
     icons.add(R.drawable.icon_11)
     icons.add(R.drawable.icon_12)
     icons.add(R.drawable.icon_13)
     icons.add(R.drawable.icon_14)
     icons.add(R.drawable.icon_15)
     icons.add(R.drawable.icon_16)
     icons.add(R.drawable.icon_17)
     icons.add(R.drawable.icon_18)
     icons.add(R.drawable.icon_19)
     icons.add(R.drawable.icon_20)
     icons.add(R.drawable.icon_6)
     icons.add(R.drawable.icon_22)

    icons.add(R.drawable.icon_locked) //23
    icons.add(R.drawable.icon_selected) //24
}

fun getImageId(num:Int):Int{
    return icons[num]
}