package com.developer.rrd_projects.person

import com.developer.rrd_projects.R

     fun getImageId(id:Int):Int {

          when(id){
               0 -> return R.drawable.icon_0
               1 ->return R.drawable.icon_1
               2 -> return R.drawable.icon_2
               3 -> return R.drawable.icon_3
               4 -> return R.drawable.icon_4
               5 -> return R.drawable.icon_5
               6 -> return R.drawable.icon_6
               7 -> return R.drawable.icon_7
               8 -> return R.drawable.icon_8
               9 -> return R.drawable.icon_9
               10 ->return R.drawable.icon_10
               11 ->return R.drawable.icon_11
               12 ->return R.drawable.icon_12
               13 ->return R.drawable.icon_13
               14 ->return R.drawable.icon_14
               15 ->return R.drawable.icon_15
               16 ->return R.drawable.icon_16
               17 ->return R.drawable.icon_17
               18 ->return R.drawable.icon_18
               19 ->return R.drawable.icon_19
               20 ->return R.drawable.icon_20
               21 ->return R.drawable.icon_21
               22 ->return R.drawable.icon_22

               23 ->return  R.drawable.icon_locked
               24 ->return R.drawable.icon_selected
          }
          return 0
     }

