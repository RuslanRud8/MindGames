package com.developer.rrd_projects.mindgames.games.anagramsGame

import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import com.developer.rrd_projects.mindgames.R
import com.developer.rrd_projects.mindgames.games.GamesActivity
import com.developer.rrd_projects.mindgames.games.fNGame.GameButton
import com.developer.rrd_projects.mindgames.games.readGameSet
import com.developer.rrd_projects.mindgames.playSound
import java.lang.StringBuilder
import java.util.*

class AnagramGame : GamesActivity() {

    private val rnd = Random()
    private var word = ""
    private val buttons: ArrayList<GameButton> = ArrayList()
    private val letters: Array<Char> = arrayOf(
        'q',
        'w',
        'e',
        'r',
        't',
        'y',
        'u',
        'i',
        'o',
        'p',
        'a',
        's',
        'd',
        'f',
        'g',
        'h',
        'j',
        'k',
        'l',
        'z',
        'x',
        'c',
        'v',
        'b',
        'b',
        'n',
        'm'
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)


        setContentView(R.layout.activity_find_num_game)

        initGame(this,"anagramGame", findViewById(R.id.start_btn), findViewById(R.id.cancel_btn), findViewById(R.id.timer))

        checkAttention(readGameSet(applicationContext).angramsGameAttention,"Your goal as soon as possible","to find anagram of given word","anagramGame",intent)

        createPreStartTimer(findViewById(R.id.dark_screen), ::generateWord)
        createGameTimer(60000,findViewById(R.id.game_timer))
    }

    private fun generateWord() {
        when {
            level < 3 -> {
                word = resources.getStringArray(R.array.anagram_words_4_letters)[rnd.nextInt(20)]
                show(2, 2)
            }

            level < 9 -> {
                word = resources.getStringArray(R.array.anagram_words_5_letters)[rnd.nextInt(20)]
                show(2, 2)
            }

            level >= 9 -> {
                word = resources.getStringArray(R.array.anagram_words_6_letters)[rnd.nextInt(20)]
                show(2, 2)
            }
        }
        val numText: TextView = findViewById(R.id.num_text)
        numText.text = word
    }

    private fun show(rowCount: Int, columnCount: Int) {

        val rl: RelativeLayout = findViewById(R.id.rl_game)
        val numText: TextView = findViewById(R.id.num_text)

        var marginTop: Float = (numText.height) * 1.1.toFloat()

        val totalWidth = (getScreenWidth() * 0.79).toInt()

        val btnHeight: Int = ((getTotalHeight() / rowCount) * 0.8).toInt()
        val btnWidth = totalWidth / columnCount

        val yTemp: Int = ((getTotalHeight() / rowCount))


        var x1 = ((getScreenWidth() - totalWidth) / (columnCount)).toFloat()
        val xTemp = x1 + btnWidth
        val tx1 = x1

        var i = 0
        var l = 0



        while (l < rowCount * columnCount) {
            val btn = GameButton(x1, marginTop, btnHeight, btnWidth, applicationContext, generateAnagram(false), 10F)
            buttons.add(btn)
            btn.btn.setOnClickListener { falseAnsListner() }
            x1 += xTemp
            rl.addView(btn.btn)
            if (i == (columnCount - 1)) {
                marginTop += yTemp
                x1 = tx1
                i = 0
            } else i++
            l++
        }
        val t = rnd.nextInt(buttons.size)

        buttons[t].btn.text = generateAnagram(true)
        buttons[t].btn.setOnClickListener { trueAnsListner() }

    }

    private fun trueAnsListner() {
        playSound(this, R.raw.menu_button_sound)

        val scoreText: TextView = findViewById(R.id.score_text)
        score += 50
        scoreText.text = score.toString()
        level++

        removeBtns()
        generateWord()
    }

    private fun falseAnsListner() {
        playSound(this, R.raw.error_sound)
        val scoreText: TextView = findViewById(R.id.score_text)
        if (score > 60) score -= 50 else score = 0
        scoreText.text = score.toString()
        if (level > 1) level--

        removeBtns()
        generateWord()
    }

    private fun generateAnagram(correct: Boolean): String {
        val stringBuilder = StringBuilder(word)

        if (!correct) {
            val t = stringBuilder[rnd.nextInt(stringBuilder.length)]
            var t2 = letters[rnd.nextInt(letters.size)]

            while (t2 == t) t2 = letters[rnd.nextInt(letters.size)]

            stringBuilder.setCharAt(stringBuilder.indexOf(t), t2)
        }

        var i = 0

        while (i < word.length / 2) {
            val startInd = rnd.nextInt(word.length)

            var endInd = rnd.nextInt(word.length)
            while (endInd == startInd) {
                endInd = rnd.nextInt(word.length)
            }

            val temp = stringBuilder[startInd]
            stringBuilder.setCharAt(startInd, stringBuilder[endInd])
            stringBuilder.setCharAt(endInd,temp)
            i++

        }

        return stringBuilder.toString()
    }

    private fun getTotalHeight(): Int {
        val numText: TextView = findViewById(R.id.num_text)
        return (getScreenHeight() - numText.height)
    }

    private fun removeBtns() {
        val rl: RelativeLayout = findViewById(R.id.rl_game)
        while (buttons.isNotEmpty()) {
            rl.removeView(buttons[buttons.size - 1].btn)
            buttons.removeAt(buttons.size - 1)
        }
    }
}
