package com.gg.project.bible

import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.gg.project.bible.databinding.ActivityMainBinding
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton

private const val PREFERENCE_TITLE_FILE = "PREFERENCE_TITLE_NAME"
private const val PREFERENCE_DESCRIPTION_FILE = "PREFERENCE_DESCRIPTION_NAME"
private const val PREFERENCE_TITLE_CURRENT = "PREFERENCE_TITLE_CURRENT"
private const val PREFERENCE_DESCRIPTION_CURRENT = "PREFERENCE_DESCRIPTION_CURRENT"

class MainActivity : AppCompatActivity() {

    // -- SharedPreferences -
    private lateinit var prefTitle: SharedPreferences
    private lateinit var prefDescription: SharedPreferences
    private lateinit var editorTitle: SharedPreferences.Editor
    private lateinit var editorDescription: SharedPreferences.Editor
    // -- SharedPreferences --

    // -- information's --
    private lateinit var tvTitle: TextView
    private lateinit var tvDescription: TextView
    private var currentValueTitle = 0
    private var currentValueDescriptions = 0
    val arrTitle by lazy { resources.getStringArray(R.array.myArrayTitle) }
    val arrDescription by lazy { resources.getStringArray(R.array.myArrayDescription) }
    // -- information's --

    // -- buttons --
    private lateinit var btnNext: MaterialButton
    private lateinit var btnNextBottom: MaterialButton
    private lateinit var btnBack: MaterialButton
    // -- buttons --

    // -- others --
    private lateinit var binding: ActivityMainBinding
    private lateinit var toolbar: MaterialToolbar
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var container: View
    private var scrollView: ScrollView? = null
    // -- others --

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        toolbar = binding.toolbar
        drawerLayout = binding.drawerLayout

        toolbar.setNavigationOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        init()

        setupDrawerAnimation()

    }

    // short code for Navigation
    private fun shortCode() {
        tvTitle.text = arrTitle[currentValueTitle]
        tvDescription.text = arrDescription[currentValueDescriptions]
        prefTitleOptimizations()
        prefDescriptionOptimizations()
        doScrollUp()
        drawerLayout.closeDrawer(GravityCompat.START)
    }

    // -- optimization SharedPreferences --
    private fun prefTitleOptimizations() {
        editorTitle = prefTitle.edit()
        editorTitle.putInt(PREFERENCE_TITLE_CURRENT, currentValueTitle)
        editorTitle.commit()
    }

    private fun prefDescriptionOptimizations() {
        tvDescription.movementMethod = ScrollingMovementMethod()
        editorDescription = prefDescription.edit()
        editorDescription.putInt(PREFERENCE_DESCRIPTION_CURRENT, currentValueDescriptions)
        editorDescription.commit()
    }
    // -- optimization SharedPreferences --

    // return scrollView up
    private fun doScrollUp() {
        scrollView?.fullScroll(ScrollView.FOCUS_UP)
        scrollView?.smoothScrollTo(0, 0)
    }

    private fun setupDrawerAnimation() {
        val scaleControl = 6f
        container = binding.container

        drawerLayout.setScrimColor(Color.TRANSPARENT)
        drawerLayout.elevation = 0f
        drawerLayout.addDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                val slideX = drawerView.width * slideOffset
                container.translationX = slideX
                container.scaleY = 1 - (slideOffset / scaleControl)
            }

            override fun onDrawerStateChanged(newState: Int) {}
            override fun onDrawerClosed(drawerView: View) {}
            override fun onDrawerOpened(drawerView: View) {}
        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        drawerLayout.closeDrawer(GravityCompat.START)
    }

    private fun init() {
        // -- initialization --
        tvTitle = binding.tvTitle
        tvDescription = binding.tvDescription
        scrollView = binding.myScrollView
        // -- initialization --

        // -- transfer data --
        prefTitle = getSharedPreferences(PREFERENCE_TITLE_FILE, MODE_PRIVATE)
        prefDescription =
            getSharedPreferences(PREFERENCE_DESCRIPTION_FILE, MODE_PRIVATE)
        currentValueTitle = prefTitle.getInt(PREFERENCE_TITLE_CURRENT, currentValueTitle)
        currentValueDescriptions =
            prefDescription.getInt(PREFERENCE_DESCRIPTION_CURRENT, currentValueDescriptions)

        tvTitle.text = arrTitle[currentValueTitle]
        tvTitle.paintFlags = tvTitle.paintFlags or Paint.UNDERLINE_TEXT_FLAG // underline
        tvDescription.text = arrDescription[currentValueDescriptions]
        tvDescription.movementMethod = ScrollingMovementMethod()
        // -- transfer data --

        // -- Listener button --
        btnNext = binding.btnNext
        btnNextBottom = binding.btnNextBottom
        btnBack = binding.btnBack

        btnNext.setOnClickListener {

            if (currentValueTitle != 1361 && currentValueDescriptions != 1361) {
                // -- change title --
                currentValueTitle++
                tvTitle.text = arrTitle[currentValueTitle]
                prefTitleOptimizations()
                // -- change title --

                // -- change descriptions --
                currentValueDescriptions++
                tvDescription.text = arrDescription[currentValueDescriptions]
                prefDescriptionOptimizations()
                // -- change descriptions --

                doScrollUp() // scroll to up on next page
            } else {

                currentValueTitle = 1361
                tvTitle.text = arrTitle[currentValueTitle]
                prefTitleOptimizations()

                currentValueDescriptions = 1361
                tvDescription.text = arrDescription[currentValueDescriptions]
                prefDescriptionOptimizations()

                doScrollUp()
            }

        }
        btnNextBottom.setOnClickListener {

            if (currentValueTitle != 1361 && currentValueDescriptions != 1361) {

                // -- change title --
                currentValueTitle++
                tvTitle.text = arrTitle[currentValueTitle]
                prefTitleOptimizations()
                // -- change title --

                // -- change descriptions --
                currentValueDescriptions++
                tvDescription.text = arrDescription[currentValueDescriptions]
                prefDescriptionOptimizations()
                // -- change descriptions --

                doScrollUp() // scroll to up on next page
            } else {

                currentValueTitle = 1361
                tvTitle.text = arrTitle[currentValueTitle]
                prefTitleOptimizations()

                currentValueDescriptions = 1361
                tvDescription.text = arrDescription[currentValueDescriptions]
                prefDescriptionOptimizations()

                doScrollUp()
            }

        }
        btnBack.setOnClickListener {

            if (currentValueTitle != 0 && currentValueDescriptions != 0) {

                currentValueTitle--
                tvTitle.text = arrTitle[currentValueTitle]
                prefTitleOptimizations()

                currentValueDescriptions--
                tvDescription.text = arrDescription[currentValueDescriptions]
                prefDescriptionOptimizations()

                doScrollUp()
            } else {
                currentValueTitle = 0
                tvTitle.text = arrTitle[currentValueTitle]
                prefTitleOptimizations()

                currentValueDescriptions = 0
                tvDescription.text = arrDescription[currentValueDescriptions]
                prefDescriptionOptimizations()

                doScrollUp()
            }

        }
        // -- Listener button --

        //-- Listener Navigation --
        binding.navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                // -- The Old Testament
                R.id.chapter_1 -> {
                    currentValueTitle = 0; currentValueDescriptions = 0; shortCode()
                }

                R.id.chapter_2 -> {
                    currentValueTitle = 1; currentValueDescriptions = 1; shortCode()
                }

                R.id.chapter_3 -> {
                    currentValueTitle = 2; currentValueDescriptions = 2; shortCode()
                }

                R.id.chapter_4 -> {
                    currentValueTitle = 3; currentValueDescriptions = 3; shortCode()
                }

                R.id.chapter_5 -> {
                    currentValueTitle = 4; currentValueDescriptions = 4; shortCode()
                }

                R.id.chapter_6 -> {
                    currentValueTitle = 5; currentValueDescriptions = 5; shortCode()
                }

                R.id.chapter_7 -> {
                    currentValueTitle = 6; currentValueDescriptions = 6; shortCode()
                }

                R.id.chapter_8 -> {
                    currentValueTitle = 7; currentValueDescriptions = 7; shortCode()
                }

                R.id.chapter_9 -> {
                    currentValueTitle = 8; currentValueDescriptions = 8; shortCode()
                }

                R.id.chapter_10 -> {
                    currentValueTitle = 9; currentValueDescriptions = 9; shortCode()
                }

                R.id.chapter_11 -> {
                    currentValueTitle = 10; currentValueDescriptions = 10; shortCode()
                }

                R.id.chapter_12 -> {
                    currentValueTitle = 11; currentValueDescriptions = 11; shortCode()
                }

                R.id.chapter_13 -> {
                    currentValueTitle = 12; currentValueDescriptions = 12; shortCode()
                }

                R.id.chapter_14 -> {
                    currentValueTitle = 13; currentValueDescriptions = 13; shortCode()
                }

                R.id.chapter_15 -> {
                    currentValueTitle = 14; currentValueDescriptions = 14; shortCode()
                }

                R.id.chapter_16 -> {
                    currentValueTitle = 15; currentValueDescriptions = 15; shortCode()
                }

                R.id.chapter_17 -> {
                    currentValueTitle = 16; currentValueDescriptions = 16; shortCode()
                }

                R.id.chapter_18 -> {
                    currentValueTitle = 17; currentValueDescriptions = 17; shortCode()
                }

                R.id.chapter_19 -> {
                    currentValueTitle = 18; currentValueDescriptions = 18; shortCode()
                }

                R.id.chapter_20 -> {
                    currentValueTitle = 19; currentValueDescriptions = 19; shortCode()
                }

                R.id.chapter_21 -> {
                    currentValueTitle = 20; currentValueDescriptions = 20; shortCode()
                }

                R.id.chapter_22 -> {
                    currentValueTitle = 21; currentValueDescriptions = 21; shortCode()
                }

                R.id.chapter_23 -> {
                    currentValueTitle = 22; currentValueDescriptions = 22; shortCode()
                }

                R.id.chapter_24 -> {
                    currentValueTitle = 23; currentValueDescriptions = 23; shortCode()
                }

                R.id.chapter_25 -> {
                    currentValueTitle = 24; currentValueDescriptions = 24; shortCode()
                }

                R.id.chapter_26 -> {
                    currentValueTitle = 25; currentValueDescriptions = 25; shortCode()
                }

                R.id.chapter_27 -> {
                    currentValueTitle = 26; currentValueDescriptions = 26; shortCode()
                }

                R.id.chapter_28 -> {
                    currentValueTitle = 27; currentValueDescriptions = 27; shortCode()
                }

                R.id.chapter_29 -> {
                    currentValueTitle = 28; currentValueDescriptions = 28; shortCode()
                }

                R.id.chapter_30 -> {
                    currentValueTitle = 29; currentValueDescriptions = 29; shortCode()
                }

                R.id.chapter_31 -> {
                    currentValueTitle = 30; currentValueDescriptions = 30; shortCode()
                }

                R.id.chapter_32 -> {
                    currentValueTitle = 31; currentValueDescriptions = 31; shortCode()
                }

                R.id.chapter_33 -> {
                    currentValueTitle = 32; currentValueDescriptions = 32; shortCode()
                }

                R.id.chapter_34 -> {
                    currentValueTitle = 33; currentValueDescriptions = 33; shortCode()
                }

                R.id.chapter_35 -> {
                    currentValueTitle = 34; currentValueDescriptions = 34; shortCode()
                }

                R.id.chapter_36 -> {
                    currentValueTitle = 35; currentValueDescriptions = 35; shortCode()
                }

                R.id.chapter_37 -> {
                    currentValueTitle = 36; currentValueDescriptions = 36; shortCode()
                }

                R.id.chapter_38 -> {
                    currentValueTitle = 37; currentValueDescriptions = 37; shortCode()
                }

                R.id.chapter_39 -> {
                    currentValueTitle = 38; currentValueDescriptions = 38; shortCode()
                }

                R.id.chapter_40 -> {
                    currentValueTitle = 39; currentValueDescriptions = 39; shortCode()
                }

                R.id.chapter_41 -> {
                    currentValueTitle = 40; currentValueDescriptions = 40; shortCode()
                }

                R.id.chapter_42 -> {
                    currentValueTitle = 41; currentValueDescriptions = 41; shortCode()
                }

                R.id.chapter_43 -> {
                    currentValueTitle = 42; currentValueDescriptions = 42; shortCode()
                }

                R.id.chapter_44 -> {
                    currentValueTitle = 43; currentValueDescriptions = 43; shortCode()
                }

                R.id.chapter_45 -> {
                    currentValueTitle = 44; currentValueDescriptions = 44; shortCode()
                }

                R.id.chapter_46 -> {
                    currentValueTitle = 45; currentValueDescriptions = 45; shortCode()
                }

                R.id.chapter_47 -> {
                    currentValueTitle = 46; currentValueDescriptions = 46; shortCode()
                }

                R.id.chapter_48 -> {
                    currentValueTitle = 47; currentValueDescriptions = 47; shortCode()
                }

                R.id.chapter_49 -> {
                    currentValueTitle = 48; currentValueDescriptions = 48; shortCode()
                }

                R.id.chapter_50 -> {
                    currentValueTitle = 49; currentValueDescriptions = 49; shortCode()
                }

                R.id.chapter_51 -> {
                    currentValueTitle = 50; currentValueDescriptions = 50; shortCode()
                }

                R.id.chapter_52 -> {
                    currentValueTitle = 51; currentValueDescriptions = 51; shortCode()
                }

                R.id.chapter_53 -> {
                    currentValueTitle = 52; currentValueDescriptions = 52; shortCode()
                }

                R.id.chapter_54 -> {
                    currentValueTitle = 53; currentValueDescriptions = 53; shortCode()
                }

                R.id.chapter_55 -> {
                    currentValueTitle = 54; currentValueDescriptions = 54; shortCode()
                }

                R.id.chapter_56 -> {
                    currentValueTitle = 55; currentValueDescriptions = 55; shortCode()
                }

                R.id.chapter_57 -> {
                    currentValueTitle = 56; currentValueDescriptions = 56; shortCode()
                }

                R.id.chapter_58 -> {
                    currentValueTitle = 57; currentValueDescriptions = 57; shortCode()
                }

                R.id.chapter_59 -> {
                    currentValueTitle = 58; currentValueDescriptions = 58; shortCode()
                }

                R.id.chapter_60 -> {
                    currentValueTitle = 59; currentValueDescriptions = 59; shortCode()
                }

                R.id.chapter_61 -> {
                    currentValueTitle = 60; currentValueDescriptions = 60; shortCode()
                }

                R.id.chapter_62 -> {
                    currentValueTitle = 61; currentValueDescriptions = 61; shortCode()
                }

                R.id.chapter_63 -> {
                    currentValueTitle = 62; currentValueDescriptions = 62; shortCode()
                }

                R.id.chapter_64 -> {
                    currentValueTitle = 63; currentValueDescriptions = 63; shortCode()
                }

                R.id.chapter_65 -> {
                    currentValueTitle = 64; currentValueDescriptions = 64; shortCode()
                }

                R.id.chapter_66 -> {
                    currentValueTitle = 65; currentValueDescriptions = 65; shortCode()
                }

                R.id.chapter_67 -> {
                    currentValueTitle = 66; currentValueDescriptions = 66; shortCode()
                }

                R.id.chapter_68 -> {
                    currentValueTitle = 67; currentValueDescriptions = 67; shortCode()
                }

                R.id.chapter_69 -> {
                    currentValueTitle = 68; currentValueDescriptions = 68; shortCode()
                }

                R.id.chapter_70 -> {
                    currentValueTitle = 69; currentValueDescriptions = 69; shortCode()
                }

                R.id.chapter_71 -> {
                    currentValueTitle = 70; currentValueDescriptions = 70; shortCode()
                }

                R.id.chapter_72 -> {
                    currentValueTitle = 71; currentValueDescriptions = 71; shortCode()
                }

                R.id.chapter_73 -> {
                    currentValueTitle = 72; currentValueDescriptions = 72; shortCode()
                }

                R.id.chapter_74 -> {
                    currentValueTitle = 73; currentValueDescriptions = 73; shortCode()
                }

                R.id.chapter_75 -> {
                    currentValueTitle = 74; currentValueDescriptions = 74; shortCode()
                }

                R.id.chapter_76 -> {
                    currentValueTitle = 75; currentValueDescriptions = 75; shortCode()
                }

                R.id.chapter_77 -> {
                    currentValueTitle = 76; currentValueDescriptions = 76; shortCode()
                }

                R.id.chapter_78 -> {
                    currentValueTitle = 77; currentValueDescriptions = 77; shortCode()
                }

                R.id.chapter_79 -> {
                    currentValueTitle = 78; currentValueDescriptions = 78; shortCode()
                }

                R.id.chapter_80 -> {
                    currentValueTitle = 79; currentValueDescriptions = 79; shortCode()
                }

                R.id.chapter_81 -> {
                    currentValueTitle = 80; currentValueDescriptions = 80; shortCode()
                }

                R.id.chapter_82 -> {
                    currentValueTitle = 81; currentValueDescriptions = 81; shortCode()
                }

                R.id.chapter_83 -> {
                    currentValueTitle = 82; currentValueDescriptions = 82; shortCode()
                }

                R.id.chapter_84 -> {
                    currentValueTitle = 83; currentValueDescriptions = 83; shortCode()
                }

                R.id.chapter_85 -> {
                    currentValueTitle = 84; currentValueDescriptions = 84; shortCode()
                }

                R.id.chapter_86 -> {
                    currentValueTitle = 85; currentValueDescriptions = 85; shortCode()
                }

                R.id.chapter_87 -> {
                    currentValueTitle = 86; currentValueDescriptions = 86; shortCode()
                }

                R.id.chapter_88 -> {
                    currentValueTitle = 87; currentValueDescriptions = 87; shortCode()
                }

                R.id.chapter_89 -> {
                    currentValueTitle = 88; currentValueDescriptions = 88; shortCode()
                }

                R.id.chapter_90 -> {
                    currentValueTitle = 89; currentValueDescriptions = 89; shortCode()
                }

                R.id.chapter_91 -> {
                    currentValueTitle = 90; currentValueDescriptions = 90; shortCode()
                }

                R.id.chapter_92 -> {
                    currentValueTitle = 91; currentValueDescriptions = 91; shortCode()
                }

                R.id.chapter_93 -> {
                    currentValueTitle = 92; currentValueDescriptions = 92; shortCode()
                }

                R.id.chapter_94 -> {
                    currentValueTitle = 93; currentValueDescriptions = 93; shortCode()
                }

                R.id.chapter_95 -> {
                    currentValueTitle = 94; currentValueDescriptions = 94; shortCode()
                }

                R.id.chapter_96 -> {
                    currentValueTitle = 95; currentValueDescriptions = 95; shortCode()
                }

                R.id.chapter_97 -> {
                    currentValueTitle = 96; currentValueDescriptions = 96; shortCode()
                }

                R.id.chapter_98 -> {
                    currentValueTitle = 97; currentValueDescriptions = 97; shortCode()
                }

                R.id.chapter_99 -> {
                    currentValueTitle = 98; currentValueDescriptions = 98; shortCode()
                }

                R.id.chapter_100 -> {
                    currentValueTitle = 99; currentValueDescriptions = 99; shortCode()
                }

                R.id.chapter_101 -> {
                    currentValueTitle = 100; currentValueDescriptions = 100; shortCode()
                }

                R.id.chapter_102 -> {
                    currentValueTitle = 101; currentValueDescriptions = 101; shortCode()
                }

                R.id.chapter_103 -> {
                    currentValueTitle = 102; currentValueDescriptions = 102; shortCode()
                }

                R.id.chapter_104 -> {
                    currentValueTitle = 103; currentValueDescriptions = 103; shortCode()
                }

                R.id.chapter_105 -> {
                    currentValueTitle = 104; currentValueDescriptions = 104; shortCode()
                }

                R.id.chapter_106 -> {
                    currentValueTitle = 105; currentValueDescriptions = 105; shortCode()
                }

                R.id.chapter_107 -> {
                    currentValueTitle = 106; currentValueDescriptions = 106; shortCode()
                }

                R.id.chapter_108 -> {
                    currentValueTitle = 107; currentValueDescriptions = 107; shortCode()
                }

                R.id.chapter_109 -> {
                    currentValueTitle = 108; currentValueDescriptions = 108; shortCode()
                }

                R.id.chapter_110 -> {
                    currentValueTitle = 109; currentValueDescriptions = 109; shortCode()
                }

                R.id.chapter_111 -> {
                    currentValueTitle = 110; currentValueDescriptions = 110; shortCode()
                }

                R.id.chapter_112 -> {
                    currentValueTitle = 111; currentValueDescriptions = 111; shortCode()
                }

                R.id.chapter_113 -> {
                    currentValueTitle = 112; currentValueDescriptions = 112; shortCode()
                }

                R.id.chapter_114 -> {
                    currentValueTitle = 113; currentValueDescriptions = 113; shortCode()
                }

                R.id.chapter_115 -> {
                    currentValueTitle = 114; currentValueDescriptions = 114; shortCode()
                }

                R.id.chapter_116 -> {
                    currentValueTitle = 115; currentValueDescriptions = 115; shortCode()
                }

                R.id.chapter_117 -> {
                    currentValueTitle = 116; currentValueDescriptions = 116; shortCode()
                }

                R.id.chapter_118 -> {
                    currentValueTitle = 117; currentValueDescriptions = 117; shortCode()
                }

                R.id.chapter_119 -> {
                    currentValueTitle = 118; currentValueDescriptions = 118; shortCode()
                }

                R.id.chapter_120 -> {
                    currentValueTitle = 119; currentValueDescriptions = 119; shortCode()
                }

                R.id.chapter_121 -> {
                    currentValueTitle = 120; currentValueDescriptions = 120; shortCode()
                }

                R.id.chapter_122 -> {
                    currentValueTitle = 121; currentValueDescriptions = 121; shortCode()
                }

                R.id.chapter_123 -> {
                    currentValueTitle = 122; currentValueDescriptions = 122; shortCode()
                }

                R.id.chapter_124 -> {
                    currentValueTitle = 123; currentValueDescriptions = 123; shortCode()
                }

                R.id.chapter_125 -> {
                    currentValueTitle = 124; currentValueDescriptions = 124; shortCode()
                }

                R.id.chapter_126 -> {
                    currentValueTitle = 125; currentValueDescriptions = 125; shortCode()
                }

                R.id.chapter_127 -> {
                    currentValueTitle = 126; currentValueDescriptions = 126; shortCode()
                }

                R.id.chapter_128 -> {
                    currentValueTitle = 127; currentValueDescriptions = 127; shortCode()
                }

                R.id.chapter_129 -> {
                    currentValueTitle = 128; currentValueDescriptions = 128; shortCode()
                }

                R.id.chapter_130 -> {
                    currentValueTitle = 129; currentValueDescriptions = 129; shortCode()
                }

                R.id.chapter_131 -> {
                    currentValueTitle = 130; currentValueDescriptions = 130; shortCode()
                }

                R.id.chapter_132 -> {
                    currentValueTitle = 131; currentValueDescriptions = 131; shortCode()
                }

                R.id.chapter_133 -> {
                    currentValueTitle = 132; currentValueDescriptions = 132; shortCode()
                }

                R.id.chapter_134 -> {
                    currentValueTitle = 133; currentValueDescriptions = 133; shortCode()
                }

                R.id.chapter_135 -> {
                    currentValueTitle = 134; currentValueDescriptions = 134; shortCode()
                }

                R.id.chapter_136 -> {
                    currentValueTitle = 135; currentValueDescriptions = 135; shortCode()
                }

                R.id.chapter_137 -> {
                    currentValueTitle = 136; currentValueDescriptions = 136; shortCode()
                }

                R.id.chapter_138 -> {
                    currentValueTitle = 137; currentValueDescriptions = 137; shortCode()
                }

                R.id.chapter_139 -> {
                    currentValueTitle = 138; currentValueDescriptions = 138; shortCode()
                }

                R.id.chapter_140 -> {
                    currentValueTitle = 139; currentValueDescriptions = 139; shortCode()
                }

                R.id.chapter_141 -> {
                    currentValueTitle = 140; currentValueDescriptions = 140; shortCode()
                }

                R.id.chapter_142 -> {
                    currentValueTitle = 141; currentValueDescriptions = 141; shortCode()
                }

                R.id.chapter_143 -> {
                    currentValueTitle = 142; currentValueDescriptions = 142; shortCode()
                }

                R.id.chapter_144 -> {
                    currentValueTitle = 143; currentValueDescriptions = 143; shortCode()
                }

                R.id.chapter_145 -> {
                    currentValueTitle = 144; currentValueDescriptions = 144; shortCode()
                }

                R.id.chapter_146 -> {
                    currentValueTitle = 145; currentValueDescriptions = 145; shortCode()
                }

                R.id.chapter_147 -> {
                    currentValueTitle = 146; currentValueDescriptions = 146; shortCode()
                }

                R.id.chapter_148 -> {
                    currentValueTitle = 147; currentValueDescriptions = 147; shortCode()
                }

                R.id.chapter_149 -> {
                    currentValueTitle = 148; currentValueDescriptions = 148; shortCode()
                }

                R.id.chapter_150 -> {
                    currentValueTitle = 149; currentValueDescriptions = 149; shortCode()
                }

                R.id.chapter_151 -> {
                    currentValueTitle = 150; currentValueDescriptions = 150; shortCode()
                }

                R.id.chapter_152 -> {
                    currentValueTitle = 151; currentValueDescriptions = 151; shortCode()
                }

                R.id.chapter_153 -> {
                    currentValueTitle = 152; currentValueDescriptions = 152; shortCode()
                }

                R.id.chapter_154 -> {
                    currentValueTitle = 153; currentValueDescriptions = 153; shortCode()
                }

                R.id.chapter_155 -> {
                    currentValueTitle = 154; currentValueDescriptions = 154; shortCode()
                }

                R.id.chapter_156 -> {
                    currentValueTitle = 155; currentValueDescriptions = 155; shortCode()
                }

                R.id.chapter_157 -> {
                    currentValueTitle = 156; currentValueDescriptions = 156; shortCode()
                }

                R.id.chapter_158 -> {
                    currentValueTitle = 157; currentValueDescriptions = 157; shortCode()
                }

                R.id.chapter_159 -> {
                    currentValueTitle = 158; currentValueDescriptions = 158; shortCode()
                }

                R.id.chapter_160 -> {
                    currentValueTitle = 159; currentValueDescriptions = 159; shortCode()
                }

                R.id.chapter_161 -> {
                    currentValueTitle = 160; currentValueDescriptions = 160; shortCode()
                }

                R.id.chapter_162 -> {
                    currentValueTitle = 161; currentValueDescriptions = 161; shortCode()
                }

                R.id.chapter_163 -> {
                    currentValueTitle = 162; currentValueDescriptions = 162; shortCode()
                }

                R.id.chapter_164 -> {
                    currentValueTitle = 163; currentValueDescriptions = 163; shortCode()
                }

                R.id.chapter_165 -> {
                    currentValueTitle = 164; currentValueDescriptions = 164; shortCode()
                }

                R.id.chapter_166 -> {
                    currentValueTitle = 165; currentValueDescriptions = 165; shortCode()
                }

                R.id.chapter_167 -> {
                    currentValueTitle = 166; currentValueDescriptions = 166; shortCode()
                }

                R.id.chapter_168 -> {
                    currentValueTitle = 167; currentValueDescriptions = 167; shortCode()
                }

                R.id.chapter_169 -> {
                    currentValueTitle = 168; currentValueDescriptions = 168; shortCode()
                }

                R.id.chapter_170 -> {
                    currentValueTitle = 169; currentValueDescriptions = 169; shortCode()
                }

                R.id.chapter_171 -> {
                    currentValueTitle = 170; currentValueDescriptions = 170; shortCode()
                }

                R.id.chapter_172 -> {
                    currentValueTitle = 171; currentValueDescriptions = 171; shortCode()
                }

                R.id.chapter_173 -> {
                    currentValueTitle = 172; currentValueDescriptions = 172; shortCode()
                }

                R.id.chapter_174 -> {
                    currentValueTitle = 173; currentValueDescriptions = 173; shortCode()
                }

                R.id.chapter_175 -> {
                    currentValueTitle = 174; currentValueDescriptions = 174; shortCode()
                }

                R.id.chapter_176 -> {
                    currentValueTitle = 175; currentValueDescriptions = 175; shortCode()
                }

                R.id.chapter_177 -> {
                    currentValueTitle = 176; currentValueDescriptions = 176; shortCode()
                }

                R.id.chapter_178 -> {
                    currentValueTitle = 177; currentValueDescriptions = 177; shortCode()
                }

                R.id.chapter_179 -> {
                    currentValueTitle = 178; currentValueDescriptions = 178; shortCode()
                }

                R.id.chapter_180 -> {
                    currentValueTitle = 179; currentValueDescriptions = 179; shortCode()
                }

                R.id.chapter_181 -> {
                    currentValueTitle = 180; currentValueDescriptions = 180; shortCode()
                }

                R.id.chapter_182 -> {
                    currentValueTitle = 181; currentValueDescriptions = 181; shortCode()
                }

                R.id.chapter_183 -> {
                    currentValueTitle = 182; currentValueDescriptions = 182; shortCode()
                }

                R.id.chapter_184 -> {
                    currentValueTitle = 183; currentValueDescriptions = 183; shortCode()
                }

                R.id.chapter_185 -> {
                    currentValueTitle = 184; currentValueDescriptions = 184; shortCode()
                }

                R.id.chapter_186 -> {
                    currentValueTitle = 185; currentValueDescriptions = 185; shortCode()
                }

                R.id.chapter_187 -> {
                    currentValueTitle = 186; currentValueDescriptions = 186; shortCode()
                }

                R.id.chapter_188 -> {
                    currentValueTitle = 187; currentValueDescriptions = 187; shortCode()
                }

                R.id.chapter_189 -> {
                    currentValueTitle = 188; currentValueDescriptions = 188; shortCode()
                }

                R.id.chapter_190 -> {
                    currentValueTitle = 189; currentValueDescriptions = 189; shortCode()
                }

                R.id.chapter_191 -> {
                    currentValueTitle = 190; currentValueDescriptions = 190; shortCode()
                }

                R.id.chapter_192 -> {
                    currentValueTitle = 191; currentValueDescriptions = 191; shortCode()
                }

                R.id.chapter_193 -> {
                    currentValueTitle = 192; currentValueDescriptions = 192; shortCode()
                }

                R.id.chapter_194 -> {
                    currentValueTitle = 193; currentValueDescriptions = 193; shortCode()
                }

                R.id.chapter_195 -> {
                    currentValueTitle = 194; currentValueDescriptions = 194; shortCode()
                }

                R.id.chapter_196 -> {
                    currentValueTitle = 195; currentValueDescriptions = 195; shortCode()
                }

                R.id.chapter_197 -> {
                    currentValueTitle = 196; currentValueDescriptions = 196; shortCode()
                }

                R.id.chapter_198 -> {
                    currentValueTitle = 197; currentValueDescriptions = 197; shortCode()
                }

                R.id.chapter_199 -> {
                    currentValueTitle = 198; currentValueDescriptions = 198; shortCode()
                }

                R.id.chapter_200 -> {
                    currentValueTitle = 199; currentValueDescriptions = 199; shortCode()
                }

                R.id.chapter_201 -> {
                    currentValueTitle = 200; currentValueDescriptions = 200; shortCode()
                }

                R.id.chapter_202 -> {
                    currentValueTitle = 201; currentValueDescriptions = 201; shortCode()
                }

                R.id.chapter_203 -> {
                    currentValueTitle = 202; currentValueDescriptions = 202; shortCode()
                }

                R.id.chapter_204 -> {
                    currentValueTitle = 203; currentValueDescriptions = 203; shortCode()
                }

                R.id.chapter_205 -> {
                    currentValueTitle = 204; currentValueDescriptions = 204; shortCode()
                }

                R.id.chapter_206 -> {
                    currentValueTitle = 205; currentValueDescriptions = 205; shortCode()
                }

                R.id.chapter_207 -> {
                    currentValueTitle = 206; currentValueDescriptions = 206; shortCode()
                }

                R.id.chapter_208 -> {
                    currentValueTitle = 207; currentValueDescriptions = 207; shortCode()
                }

                R.id.chapter_209 -> {
                    currentValueTitle = 208; currentValueDescriptions = 208; shortCode()
                }

                R.id.chapter_210 -> {
                    currentValueTitle = 209; currentValueDescriptions = 209; shortCode()
                }

                R.id.chapter_211 -> {
                    currentValueTitle = 210; currentValueDescriptions = 210; shortCode()
                }

                R.id.chapter_212 -> {
                    currentValueTitle = 211; currentValueDescriptions = 211; shortCode()
                }

                R.id.chapter_213 -> {
                    currentValueTitle = 212; currentValueDescriptions = 212; shortCode()
                }

                R.id.chapter_214 -> {
                    currentValueTitle = 213; currentValueDescriptions = 213; shortCode()
                }

                R.id.chapter_215 -> {
                    currentValueTitle = 214; currentValueDescriptions = 214; shortCode()
                }

                R.id.chapter_216 -> {
                    currentValueTitle = 215; currentValueDescriptions = 215; shortCode()
                }

                R.id.chapter_217 -> {
                    currentValueTitle = 216; currentValueDescriptions = 216; shortCode()
                }

                R.id.chapter_218 -> {
                    currentValueTitle = 217; currentValueDescriptions = 217; shortCode()
                }

                R.id.chapter_219 -> {
                    currentValueTitle = 218; currentValueDescriptions = 218; shortCode()
                }

                R.id.chapter_220 -> {
                    currentValueTitle = 219; currentValueDescriptions = 219; shortCode()
                }

                R.id.chapter_221 -> {
                    currentValueTitle = 220; currentValueDescriptions = 220; shortCode()
                }

                R.id.chapter_222 -> {
                    currentValueTitle = 221; currentValueDescriptions = 221; shortCode()
                }

                R.id.chapter_223 -> {
                    currentValueTitle = 222; currentValueDescriptions = 222; shortCode()
                }

                R.id.chapter_224 -> {
                    currentValueTitle = 223; currentValueDescriptions = 223; shortCode()
                }

                R.id.chapter_225 -> {
                    currentValueTitle = 224; currentValueDescriptions = 224; shortCode()
                }

                R.id.chapter_226 -> {
                    currentValueTitle = 225; currentValueDescriptions = 225; shortCode()
                }

                R.id.chapter_227 -> {
                    currentValueTitle = 226; currentValueDescriptions = 226; shortCode()
                }

                R.id.chapter_228 -> {
                    currentValueTitle = 227; currentValueDescriptions = 227; shortCode()
                }

                R.id.chapter_229 -> {
                    currentValueTitle = 228; currentValueDescriptions = 228; shortCode()
                }

                R.id.chapter_230 -> {
                    currentValueTitle = 229; currentValueDescriptions = 229; shortCode()
                }

                R.id.chapter_231 -> {
                    currentValueTitle = 230; currentValueDescriptions = 230; shortCode()
                }

                R.id.chapter_232 -> {
                    currentValueTitle = 231; currentValueDescriptions = 231; shortCode()
                }

                R.id.chapter_233 -> {
                    currentValueTitle = 232; currentValueDescriptions = 232; shortCode()
                }

                R.id.chapter_234 -> {
                    currentValueTitle = 233; currentValueDescriptions = 233; shortCode()
                }

                R.id.chapter_235 -> {
                    currentValueTitle = 234; currentValueDescriptions = 234; shortCode()
                }

                R.id.chapter_236 -> {
                    currentValueTitle = 235; currentValueDescriptions = 235; shortCode()
                }

                R.id.chapter_237 -> {
                    currentValueTitle = 236; currentValueDescriptions = 236; shortCode()
                }

                R.id.chapter_238 -> {
                    currentValueTitle = 237; currentValueDescriptions = 237; shortCode()
                }

                R.id.chapter_239 -> {
                    currentValueTitle = 238; currentValueDescriptions = 238; shortCode()
                }

                R.id.chapter_240 -> {
                    currentValueTitle = 239; currentValueDescriptions = 239; shortCode()
                }

                R.id.chapter_241 -> {
                    currentValueTitle = 240; currentValueDescriptions = 240; shortCode()
                }

                R.id.chapter_242 -> {
                    currentValueTitle = 241; currentValueDescriptions = 241; shortCode()
                }

                R.id.chapter_243 -> {
                    currentValueTitle = 242; currentValueDescriptions = 242; shortCode()
                }

                R.id.chapter_244 -> {
                    currentValueTitle = 243; currentValueDescriptions = 243; shortCode()
                }

                R.id.chapter_245 -> {
                    currentValueTitle = 244; currentValueDescriptions = 244; shortCode()
                }

                R.id.chapter_246 -> {
                    currentValueTitle = 245; currentValueDescriptions = 245; shortCode()
                }

                R.id.chapter_247 -> {
                    currentValueTitle = 246; currentValueDescriptions = 246; shortCode()
                }

                R.id.chapter_248 -> {
                    currentValueTitle = 247; currentValueDescriptions = 247; shortCode()
                }

                R.id.chapter_249 -> {
                    currentValueTitle = 248; currentValueDescriptions = 248; shortCode()
                }

                R.id.chapter_250 -> {
                    currentValueTitle = 249; currentValueDescriptions = 249; shortCode()
                }

                R.id.chapter_251 -> {
                    currentValueTitle = 250; currentValueDescriptions = 250; shortCode()
                }

                R.id.chapter_252 -> {
                    currentValueTitle = 251; currentValueDescriptions = 251; shortCode()
                }

                R.id.chapter_253 -> {
                    currentValueTitle = 252; currentValueDescriptions = 252; shortCode()
                }

                R.id.chapter_254 -> {
                    currentValueTitle = 253; currentValueDescriptions = 253; shortCode()
                }

                R.id.chapter_255 -> {
                    currentValueTitle = 254; currentValueDescriptions = 254; shortCode()
                }

                R.id.chapter_256 -> {
                    currentValueTitle = 255; currentValueDescriptions = 255; shortCode()
                }

                R.id.chapter_257 -> {
                    currentValueTitle = 256; currentValueDescriptions = 256; shortCode()
                }

                R.id.chapter_258 -> {
                    currentValueTitle = 257; currentValueDescriptions = 257; shortCode()
                }

                R.id.chapter_259 -> {
                    currentValueTitle = 258; currentValueDescriptions = 258; shortCode()
                }

                R.id.chapter_260 -> {
                    currentValueTitle = 259; currentValueDescriptions = 259; shortCode()
                }

                R.id.chapter_261 -> {
                    currentValueTitle = 260; currentValueDescriptions = 260; shortCode()
                }

                R.id.chapter_262 -> {
                    currentValueTitle = 261; currentValueDescriptions = 261; shortCode()
                }

                R.id.chapter_263 -> {
                    currentValueTitle = 262; currentValueDescriptions = 262; shortCode()
                }

                R.id.chapter_264 -> {
                    currentValueTitle = 263; currentValueDescriptions = 263; shortCode()
                }

                R.id.chapter_265 -> {
                    currentValueTitle = 264; currentValueDescriptions = 264; shortCode()
                }

                R.id.chapter_266 -> {
                    currentValueTitle = 265; currentValueDescriptions = 265; shortCode()
                }

                R.id.chapter_267 -> {
                    currentValueTitle = 266; currentValueDescriptions = 266; shortCode()
                }

                R.id.chapter_268 -> {
                    currentValueTitle = 267; currentValueDescriptions = 267; shortCode()
                }

                R.id.chapter_269 -> {
                    currentValueTitle = 268; currentValueDescriptions = 268; shortCode()
                }

                R.id.chapter_270 -> {
                    currentValueTitle = 269; currentValueDescriptions = 269; shortCode()
                }

                R.id.chapter_271 -> {
                    currentValueTitle = 270; currentValueDescriptions = 270; shortCode()
                }

                R.id.chapter_272 -> {
                    currentValueTitle = 271; currentValueDescriptions = 271; shortCode()
                }

                R.id.chapter_273 -> {
                    currentValueTitle = 272; currentValueDescriptions = 272; shortCode()
                }

                R.id.chapter_274 -> {
                    currentValueTitle = 273; currentValueDescriptions = 273; shortCode()
                }

                R.id.chapter_275 -> {
                    currentValueTitle = 274; currentValueDescriptions = 274; shortCode()
                }

                R.id.chapter_276 -> {
                    currentValueTitle = 275; currentValueDescriptions = 275; shortCode()
                }

                R.id.chapter_277 -> {
                    currentValueTitle = 276; currentValueDescriptions = 276; shortCode()
                }

                R.id.chapter_278 -> {
                    currentValueTitle = 277; currentValueDescriptions = 277; shortCode()
                }

                R.id.chapter_279 -> {
                    currentValueTitle = 278; currentValueDescriptions = 278; shortCode()
                }

                R.id.chapter_280 -> {
                    currentValueTitle = 279; currentValueDescriptions = 279; shortCode()
                }

                R.id.chapter_281 -> {
                    currentValueTitle = 280; currentValueDescriptions = 280; shortCode()
                }

                R.id.chapter_282 -> {
                    currentValueTitle = 281; currentValueDescriptions = 281; shortCode()
                }

                R.id.chapter_283 -> {
                    currentValueTitle = 282; currentValueDescriptions = 282; shortCode()
                }

                R.id.chapter_284 -> {
                    currentValueTitle = 283; currentValueDescriptions = 283; shortCode()
                }

                R.id.chapter_285 -> {
                    currentValueTitle = 284; currentValueDescriptions = 284; shortCode()
                }

                R.id.chapter_286 -> {
                    currentValueTitle = 285; currentValueDescriptions = 285; shortCode()
                }

                R.id.chapter_287 -> {
                    currentValueTitle = 286; currentValueDescriptions = 286; shortCode()
                }

                R.id.chapter_288 -> {
                    currentValueTitle = 287; currentValueDescriptions = 287; shortCode()
                }

                R.id.chapter_289 -> {
                    currentValueTitle = 288; currentValueDescriptions = 288; shortCode()
                }

                R.id.chapter_290 -> {
                    currentValueTitle = 289; currentValueDescriptions = 289; shortCode()
                }

                R.id.chapter_291 -> {
                    currentValueTitle = 290; currentValueDescriptions = 290; shortCode()
                }

                R.id.chapter_292 -> {
                    currentValueTitle = 291; currentValueDescriptions = 291; shortCode()
                }

                R.id.chapter_293 -> {
                    currentValueTitle = 292; currentValueDescriptions = 292; shortCode()
                }

                R.id.chapter_294 -> {
                    currentValueTitle = 293; currentValueDescriptions = 293; shortCode()
                }

                R.id.chapter_295 -> {
                    currentValueTitle = 294; currentValueDescriptions = 294; shortCode()
                }

                R.id.chapter_296 -> {
                    currentValueTitle = 295; currentValueDescriptions = 295; shortCode()
                }

                R.id.chapter_297 -> {
                    currentValueTitle = 296; currentValueDescriptions = 296; shortCode()
                }

                R.id.chapter_298 -> {
                    currentValueTitle = 297; currentValueDescriptions = 297; shortCode()
                }

                R.id.chapter_299 -> {
                    currentValueTitle = 298; currentValueDescriptions = 298; shortCode()
                }

                R.id.chapter_300 -> {
                    currentValueTitle = 299; currentValueDescriptions = 299; shortCode()
                }

                R.id.chapter_301 -> {
                    currentValueTitle = 300; currentValueDescriptions = 300; shortCode()
                }

                R.id.chapter_302 -> {
                    currentValueTitle = 301; currentValueDescriptions = 301; shortCode()
                }

                R.id.chapter_303 -> {
                    currentValueTitle = 302; currentValueDescriptions = 302; shortCode()
                }

                R.id.chapter_304 -> {
                    currentValueTitle = 303; currentValueDescriptions = 303; shortCode()
                }

                R.id.chapter_305 -> {
                    currentValueTitle = 304; currentValueDescriptions = 304; shortCode()
                }

                R.id.chapter_306 -> {
                    currentValueTitle = 305; currentValueDescriptions = 305; shortCode()
                }

                R.id.chapter_307 -> {
                    currentValueTitle = 306; currentValueDescriptions = 306; shortCode()
                }

                R.id.chapter_308 -> {
                    currentValueTitle = 307; currentValueDescriptions = 307; shortCode()
                }

                R.id.chapter_309 -> {
                    currentValueTitle = 308; currentValueDescriptions = 308; shortCode()
                }

                R.id.chapter_310 -> {
                    currentValueTitle = 309; currentValueDescriptions = 309; shortCode()
                }

                R.id.chapter_311 -> {
                    currentValueTitle = 310; currentValueDescriptions = 310; shortCode()
                }

                R.id.chapter_312 -> {
                    currentValueTitle = 311; currentValueDescriptions = 311; shortCode()
                }

                R.id.chapter_313 -> {
                    currentValueTitle = 312; currentValueDescriptions = 312; shortCode()
                }

                R.id.chapter_314 -> {
                    currentValueTitle = 313; currentValueDescriptions = 313; shortCode()
                }

                R.id.chapter_315 -> {
                    currentValueTitle = 314; currentValueDescriptions = 314; shortCode()
                }

                R.id.chapter_316 -> {
                    currentValueTitle = 315; currentValueDescriptions = 315; shortCode()
                }

                R.id.chapter_317 -> {
                    currentValueTitle = 316; currentValueDescriptions = 316; shortCode()
                }

                R.id.chapter_318 -> {
                    currentValueTitle = 317; currentValueDescriptions = 317; shortCode()
                }

                R.id.chapter_319 -> {
                    currentValueTitle = 318; currentValueDescriptions = 318; shortCode()
                }

                R.id.chapter_320 -> {
                    currentValueTitle = 319; currentValueDescriptions = 319; shortCode()
                }

                R.id.chapter_321 -> {
                    currentValueTitle = 320; currentValueDescriptions = 320; shortCode()
                }

                R.id.chapter_322 -> {
                    currentValueTitle = 321; currentValueDescriptions = 321; shortCode()
                }

                R.id.chapter_323 -> {
                    currentValueTitle = 322; currentValueDescriptions = 322; shortCode()
                }

                R.id.chapter_324 -> {
                    currentValueTitle = 323; currentValueDescriptions = 323; shortCode()
                }

                R.id.chapter_325 -> {
                    currentValueTitle = 324; currentValueDescriptions = 324; shortCode()
                }

                R.id.chapter_326 -> {
                    currentValueTitle = 325; currentValueDescriptions = 325; shortCode()
                }

                R.id.chapter_327 -> {
                    currentValueTitle = 326; currentValueDescriptions = 326; shortCode()
                }

                R.id.chapter_328 -> {
                    currentValueTitle = 327; currentValueDescriptions = 327; shortCode()
                }

                R.id.chapter_329 -> {
                    currentValueTitle = 328; currentValueDescriptions = 328; shortCode()
                }

                R.id.chapter_330 -> {
                    currentValueTitle = 329; currentValueDescriptions = 329; shortCode()
                }

                R.id.chapter_331 -> {
                    currentValueTitle = 330; currentValueDescriptions = 330; shortCode()
                }

                R.id.chapter_332 -> {
                    currentValueTitle = 331; currentValueDescriptions = 331; shortCode()
                }

                R.id.chapter_333 -> {
                    currentValueTitle = 332; currentValueDescriptions = 332; shortCode()
                }

                R.id.chapter_334 -> {
                    currentValueTitle = 333; currentValueDescriptions = 333; shortCode()
                }

                R.id.chapter_335 -> {
                    currentValueTitle = 334; currentValueDescriptions = 334; shortCode()
                }

                R.id.chapter_336 -> {
                    currentValueTitle = 335; currentValueDescriptions = 335; shortCode()
                }

                R.id.chapter_337 -> {
                    currentValueTitle = 336; currentValueDescriptions = 336; shortCode()
                }

                R.id.chapter_338 -> {
                    currentValueTitle = 337; currentValueDescriptions = 337; shortCode()
                }

                R.id.chapter_339 -> {
                    currentValueTitle = 338; currentValueDescriptions = 338; shortCode()
                }

                R.id.chapter_340 -> {
                    currentValueTitle = 339; currentValueDescriptions = 339; shortCode()
                }

                R.id.chapter_341 -> {
                    currentValueTitle = 340; currentValueDescriptions = 340; shortCode()
                }

                R.id.chapter_342 -> {
                    currentValueTitle = 341; currentValueDescriptions = 341; shortCode()
                }

                R.id.chapter_343 -> {
                    currentValueTitle = 342; currentValueDescriptions = 342; shortCode()
                }

                R.id.chapter_344 -> {
                    currentValueTitle = 343; currentValueDescriptions = 343; shortCode()
                }

                R.id.chapter_345 -> {
                    currentValueTitle = 344; currentValueDescriptions = 344; shortCode()
                }

                R.id.chapter_346 -> {
                    currentValueTitle = 345; currentValueDescriptions = 345; shortCode()
                }

                R.id.chapter_347 -> {
                    currentValueTitle = 346; currentValueDescriptions = 346; shortCode()
                }

                R.id.chapter_348 -> {
                    currentValueTitle = 347; currentValueDescriptions = 347; shortCode()
                }

                R.id.chapter_349 -> {
                    currentValueTitle = 348; currentValueDescriptions = 348; shortCode()
                }

                R.id.chapter_350 -> {
                    currentValueTitle = 349; currentValueDescriptions = 349; shortCode()
                }

                R.id.chapter_351 -> {
                    currentValueTitle = 350; currentValueDescriptions = 350; shortCode()
                }

                R.id.chapter_352 -> {
                    currentValueTitle = 351; currentValueDescriptions = 351; shortCode()
                }

                R.id.chapter_353 -> {
                    currentValueTitle = 352; currentValueDescriptions = 352; shortCode()
                }

                R.id.chapter_354 -> {
                    currentValueTitle = 353; currentValueDescriptions = 353; shortCode()
                }

                R.id.chapter_355 -> {
                    currentValueTitle = 354; currentValueDescriptions = 354; shortCode()
                }

                R.id.chapter_356 -> {
                    currentValueTitle = 355; currentValueDescriptions = 355; shortCode()
                }

                R.id.chapter_357 -> {
                    currentValueTitle = 356; currentValueDescriptions = 356; shortCode()
                }

                R.id.chapter_358 -> {
                    currentValueTitle = 357; currentValueDescriptions = 357; shortCode()
                }

                R.id.chapter_359 -> {
                    currentValueTitle = 358; currentValueDescriptions = 358; shortCode()
                }

                R.id.chapter_360 -> {
                    currentValueTitle = 359; currentValueDescriptions = 359; shortCode()
                }

                R.id.chapter_361 -> {
                    currentValueTitle = 360; currentValueDescriptions = 360; shortCode()
                }

                R.id.chapter_362 -> {
                    currentValueTitle = 361; currentValueDescriptions = 361; shortCode()
                }

                R.id.chapter_363 -> {
                    currentValueTitle = 362; currentValueDescriptions = 362; shortCode()
                }

                R.id.chapter_364 -> {
                    currentValueTitle = 363; currentValueDescriptions = 363; shortCode()
                }

                R.id.chapter_365 -> {
                    currentValueTitle = 364; currentValueDescriptions = 364; shortCode()
                }

                R.id.chapter_366 -> {
                    currentValueTitle = 365; currentValueDescriptions = 365; shortCode()
                }

                R.id.chapter_367 -> {
                    currentValueTitle = 366; currentValueDescriptions = 366; shortCode()
                }

                R.id.chapter_368 -> {
                    currentValueTitle = 367; currentValueDescriptions = 367; shortCode()
                }

                R.id.chapter_369 -> {
                    currentValueTitle = 368; currentValueDescriptions = 368; shortCode()
                }

                R.id.chapter_370 -> {
                    currentValueTitle = 369; currentValueDescriptions = 369; shortCode()
                }

                R.id.chapter_371 -> {
                    currentValueTitle = 370; currentValueDescriptions = 370; shortCode()
                }

                R.id.chapter_372 -> {
                    currentValueTitle = 371; currentValueDescriptions = 371; shortCode()
                }

                R.id.chapter_373 -> {
                    currentValueTitle = 372; currentValueDescriptions = 372; shortCode()
                }

                R.id.chapter_374 -> {
                    currentValueTitle = 373; currentValueDescriptions = 373; shortCode()
                }

                R.id.chapter_375 -> {
                    currentValueTitle = 374; currentValueDescriptions = 374; shortCode()
                }

                R.id.chapter_376 -> {
                    currentValueTitle = 375; currentValueDescriptions = 375; shortCode()
                }

                R.id.chapter_377 -> {
                    currentValueTitle = 376; currentValueDescriptions = 376; shortCode()
                }

                R.id.chapter_378 -> {
                    currentValueTitle = 377; currentValueDescriptions = 377; shortCode()
                }

                R.id.chapter_379 -> {
                    currentValueTitle = 378; currentValueDescriptions = 378; shortCode()
                }

                R.id.chapter_380 -> {
                    currentValueTitle = 379; currentValueDescriptions = 379; shortCode()
                }

                R.id.chapter_381 -> {
                    currentValueTitle = 380; currentValueDescriptions = 380; shortCode()
                }

                R.id.chapter_382 -> {
                    currentValueTitle = 381; currentValueDescriptions = 381; shortCode()
                }

                R.id.chapter_383 -> {
                    currentValueTitle = 382; currentValueDescriptions = 382; shortCode()
                }

                R.id.chapter_384 -> {
                    currentValueTitle = 383; currentValueDescriptions = 383; shortCode()
                }

                R.id.chapter_385 -> {
                    currentValueTitle = 384; currentValueDescriptions = 384; shortCode()
                }

                R.id.chapter_386 -> {
                    currentValueTitle = 385; currentValueDescriptions = 385; shortCode()
                }

                R.id.chapter_387 -> {
                    currentValueTitle = 386; currentValueDescriptions = 386; shortCode()
                }

                R.id.chapter_388 -> {
                    currentValueTitle = 387; currentValueDescriptions = 387; shortCode()
                }

                R.id.chapter_389 -> {
                    currentValueTitle = 388; currentValueDescriptions = 388; shortCode()
                }

                R.id.chapter_390 -> {
                    currentValueTitle = 389; currentValueDescriptions = 389; shortCode()
                }

                R.id.chapter_391 -> {
                    currentValueTitle = 390; currentValueDescriptions = 390; shortCode()
                }

                R.id.chapter_392 -> {
                    currentValueTitle = 391; currentValueDescriptions = 391; shortCode()
                }

                R.id.chapter_393 -> {
                    currentValueTitle = 392; currentValueDescriptions = 392; shortCode()
                }

                R.id.chapter_394 -> {
                    currentValueTitle = 393; currentValueDescriptions = 393; shortCode()
                }

                R.id.chapter_395 -> {
                    currentValueTitle = 394; currentValueDescriptions = 394; shortCode()
                }

                R.id.chapter_396 -> {
                    currentValueTitle = 395; currentValueDescriptions = 395; shortCode()
                }

                R.id.chapter_397 -> {
                    currentValueTitle = 396; currentValueDescriptions = 396; shortCode()
                }

                R.id.chapter_398 -> {
                    currentValueTitle = 397; currentValueDescriptions = 397; shortCode()
                }

                R.id.chapter_399 -> {
                    currentValueTitle = 398; currentValueDescriptions = 398; shortCode()
                }

                R.id.chapter_400 -> {
                    currentValueTitle = 399; currentValueDescriptions = 399; shortCode()
                }

                R.id.chapter_401 -> {
                    currentValueTitle = 400; currentValueDescriptions = 400; shortCode()
                }

                R.id.chapter_402 -> {
                    currentValueTitle = 401; currentValueDescriptions = 401; shortCode()
                }

                R.id.chapter_403 -> {
                    currentValueTitle = 402; currentValueDescriptions = 402; shortCode()
                }

                R.id.chapter_404 -> {
                    currentValueTitle = 403; currentValueDescriptions = 403; shortCode()
                }

                R.id.chapter_405 -> {
                    currentValueTitle = 404; currentValueDescriptions = 404; shortCode()
                }

                R.id.chapter_406 -> {
                    currentValueTitle = 405; currentValueDescriptions = 405; shortCode()
                }

                R.id.chapter_407 -> {
                    currentValueTitle = 406; currentValueDescriptions = 406; shortCode()
                }

                R.id.chapter_408 -> {
                    currentValueTitle = 407; currentValueDescriptions = 407; shortCode()
                }

                R.id.chapter_409 -> {
                    currentValueTitle = 408; currentValueDescriptions = 408; shortCode()
                }

                R.id.chapter_410 -> {
                    currentValueTitle = 409; currentValueDescriptions = 409; shortCode()
                }

                R.id.chapter_411 -> {
                    currentValueTitle = 410; currentValueDescriptions = 410; shortCode()
                }

                R.id.chapter_412 -> {
                    currentValueTitle = 411; currentValueDescriptions = 411; shortCode()
                }

                R.id.chapter_413 -> {
                    currentValueTitle = 412; currentValueDescriptions = 412; shortCode()
                }

                R.id.chapter_414 -> {
                    currentValueTitle = 413; currentValueDescriptions = 413; shortCode()
                }

                R.id.chapter_415 -> {
                    currentValueTitle = 414; currentValueDescriptions = 414; shortCode()
                }

                R.id.chapter_416 -> {
                    currentValueTitle = 415; currentValueDescriptions = 415; shortCode()
                }

                R.id.chapter_417 -> {
                    currentValueTitle = 416; currentValueDescriptions = 416; shortCode()
                }

                R.id.chapter_418 -> {
                    currentValueTitle = 417; currentValueDescriptions = 417; shortCode()
                }

                R.id.chapter_419 -> {
                    currentValueTitle = 418; currentValueDescriptions = 418; shortCode()
                }

                R.id.chapter_420 -> {
                    currentValueTitle = 419; currentValueDescriptions = 419; shortCode()
                }

                R.id.chapter_421 -> {
                    currentValueTitle = 420; currentValueDescriptions = 420; shortCode()
                }

                R.id.chapter_422 -> {
                    currentValueTitle = 421; currentValueDescriptions = 421; shortCode()
                }

                R.id.chapter_423 -> {
                    currentValueTitle = 422; currentValueDescriptions = 422; shortCode()
                }

                R.id.chapter_424 -> {
                    currentValueTitle = 423; currentValueDescriptions = 423; shortCode()
                }

                R.id.chapter_425 -> {
                    currentValueTitle = 424; currentValueDescriptions = 424; shortCode()
                }

                R.id.chapter_426 -> {
                    currentValueTitle = 425; currentValueDescriptions = 425; shortCode()
                }

                R.id.chapter_427 -> {
                    currentValueTitle = 426; currentValueDescriptions = 426; shortCode()
                }

                R.id.chapter_428 -> {
                    currentValueTitle = 427; currentValueDescriptions = 427; shortCode()
                }

                R.id.chapter_429 -> {
                    currentValueTitle = 428; currentValueDescriptions = 428; shortCode()
                }

                R.id.chapter_430 -> {
                    currentValueTitle = 429; currentValueDescriptions = 429; shortCode()
                }

                R.id.chapter_431 -> {
                    currentValueTitle = 430; currentValueDescriptions = 430; shortCode()
                }

                R.id.chapter_432 -> {
                    currentValueTitle = 431; currentValueDescriptions = 431; shortCode()
                }

                R.id.chapter_433 -> {
                    currentValueTitle = 432; currentValueDescriptions = 432; shortCode()
                }

                R.id.chapter_434 -> {
                    currentValueTitle = 433; currentValueDescriptions = 433; shortCode()
                }

                R.id.chapter_435 -> {
                    currentValueTitle = 434; currentValueDescriptions = 434; shortCode()
                }

                R.id.chapter_436 -> {
                    currentValueTitle = 435; currentValueDescriptions = 435; shortCode()
                }

                R.id.chapter_437 -> {
                    currentValueTitle = 436; currentValueDescriptions = 436; shortCode()
                }

                R.id.chapter_438 -> {
                    currentValueTitle = 437; currentValueDescriptions = 437; shortCode()
                }

                R.id.chapter_439 -> {
                    currentValueTitle = 438; currentValueDescriptions = 438; shortCode()
                }

                R.id.chapter_440 -> {
                    currentValueTitle = 439; currentValueDescriptions = 439; shortCode()
                }

                R.id.chapter_441 -> {
                    currentValueTitle = 440; currentValueDescriptions = 440; shortCode()
                }

                R.id.chapter_442 -> {
                    currentValueTitle = 441; currentValueDescriptions = 441; shortCode()
                }

                R.id.chapter_443 -> {
                    currentValueTitle = 442; currentValueDescriptions = 442; shortCode()
                }

                R.id.chapter_444 -> {
                    currentValueTitle = 443; currentValueDescriptions = 443; shortCode()
                }

                R.id.chapter_445 -> {
                    currentValueTitle = 444; currentValueDescriptions = 444; shortCode()
                }

                R.id.chapter_446 -> {
                    currentValueTitle = 445; currentValueDescriptions = 445; shortCode()
                }

                R.id.chapter_447 -> {
                    currentValueTitle = 446; currentValueDescriptions = 44; shortCode()
                }

                R.id.chapter_448 -> {
                    currentValueTitle = 447; currentValueDescriptions = 447; shortCode()
                }

                R.id.chapter_449 -> {
                    currentValueTitle = 448; currentValueDescriptions = 448; shortCode()
                }

                R.id.chapter_450 -> {
                    currentValueTitle = 449; currentValueDescriptions = 449; shortCode()
                }

                R.id.chapter_451 -> {
                    currentValueTitle = 450; currentValueDescriptions = 450; shortCode()
                }

                R.id.chapter_452 -> {
                    currentValueTitle = 451; currentValueDescriptions = 451; shortCode()
                }

                R.id.chapter_453 -> {
                    currentValueTitle = 452; currentValueDescriptions = 452; shortCode()
                }

                R.id.chapter_454 -> {
                    currentValueTitle = 453; currentValueDescriptions = 453; shortCode()
                }

                R.id.chapter_455 -> {
                    currentValueTitle = 454; currentValueDescriptions = 454; shortCode()
                }

                R.id.chapter_456 -> {
                    currentValueTitle = 455; currentValueDescriptions = 455; shortCode()
                }

                R.id.chapter_457 -> {
                    currentValueTitle = 456; currentValueDescriptions = 456; shortCode()
                }

                R.id.chapter_458 -> {
                    currentValueTitle = 457; currentValueDescriptions = 457; shortCode()
                }

                R.id.chapter_459 -> {
                    currentValueTitle = 458; currentValueDescriptions = 458; shortCode()
                }

                R.id.chapter_460 -> {
                    currentValueTitle = 459; currentValueDescriptions = 459; shortCode()
                }

                R.id.chapter_461 -> {
                    currentValueTitle = 460; currentValueDescriptions = 460; shortCode()
                }

                R.id.chapter_462 -> {
                    currentValueTitle = 461; currentValueDescriptions = 461; shortCode()
                }

                R.id.chapter_463 -> {
                    currentValueTitle = 462; currentValueDescriptions = 462; shortCode()
                }

                R.id.chapter_464 -> {
                    currentValueTitle = 463; currentValueDescriptions = 463; shortCode()
                }

                R.id.chapter_465 -> {
                    currentValueTitle = 464; currentValueDescriptions = 464; shortCode()
                }

                R.id.chapter_466 -> {
                    currentValueTitle = 465; currentValueDescriptions = 465; shortCode()
                }

                R.id.chapter_467 -> {
                    currentValueTitle = 466; currentValueDescriptions = 466; shortCode()
                }

                R.id.chapter_468 -> {
                    currentValueTitle = 467; currentValueDescriptions = 467; shortCode()
                }

                R.id.chapter_469 -> {
                    currentValueTitle = 468; currentValueDescriptions = 468; shortCode()
                }

                R.id.chapter_470 -> {
                    currentValueTitle = 469; currentValueDescriptions = 469; shortCode()
                }

                R.id.chapter_471 -> {
                    currentValueTitle = 470; currentValueDescriptions = 470; shortCode()
                }

                R.id.chapter_472 -> {
                    currentValueTitle = 471; currentValueDescriptions = 471; shortCode()
                }

                R.id.chapter_473 -> {
                    currentValueTitle = 472; currentValueDescriptions = 472; shortCode()
                }

                R.id.chapter_474 -> {
                    currentValueTitle = 473; currentValueDescriptions = 473; shortCode()
                }

                R.id.chapter_475 -> {
                    currentValueTitle = 474; currentValueDescriptions = 474; shortCode()
                }

                R.id.chapter_476 -> {
                    currentValueTitle = 475; currentValueDescriptions = 475; shortCode()
                }

                R.id.chapter_477 -> {
                    currentValueTitle = 476; currentValueDescriptions = 476; shortCode()
                }

                R.id.chapter_478 -> {
                    currentValueTitle = 477; currentValueDescriptions = 477; shortCode()
                }

                R.id.chapter_479 -> {
                    currentValueTitle = 478; currentValueDescriptions = 478; shortCode()
                }

                R.id.chapter_480 -> {
                    currentValueTitle = 479; currentValueDescriptions = 479; shortCode()
                }

                R.id.chapter_481 -> {
                    currentValueTitle = 480; currentValueDescriptions = 480; shortCode()
                }

                R.id.chapter_482 -> {
                    currentValueTitle = 481; currentValueDescriptions = 481; shortCode()
                }

                R.id.chapter_483 -> {
                    currentValueTitle = 482; currentValueDescriptions = 482; shortCode()
                }

                R.id.chapter_484 -> {
                    currentValueTitle = 483; currentValueDescriptions = 483; shortCode()
                }

                R.id.chapter_485 -> {
                    currentValueTitle = 484; currentValueDescriptions = 484; shortCode()
                }

                R.id.chapter_486 -> {
                    currentValueTitle = 485; currentValueDescriptions = 485; shortCode()
                }

                R.id.chapter_487 -> {
                    currentValueTitle = 486; currentValueDescriptions = 486; shortCode()
                }

                R.id.chapter_488 -> {
                    currentValueTitle = 487; currentValueDescriptions = 487; shortCode()
                }

                R.id.chapter_489 -> {
                    currentValueTitle = 488; currentValueDescriptions = 488; shortCode()
                }

                R.id.chapter_490 -> {
                    currentValueTitle = 489; currentValueDescriptions = 489; shortCode()
                }

                R.id.chapter_491 -> {
                    currentValueTitle = 490; currentValueDescriptions = 490; shortCode()
                }

                R.id.chapter_492 -> {
                    currentValueTitle = 491; currentValueDescriptions = 491; shortCode()
                }

                R.id.chapter_493 -> {
                    currentValueTitle = 492; currentValueDescriptions = 492; shortCode()
                }

                R.id.chapter_494 -> {
                    currentValueTitle = 493; currentValueDescriptions = 493; shortCode()
                }

                R.id.chapter_495 -> {
                    currentValueTitle = 494; currentValueDescriptions = 494; shortCode()
                }

                R.id.chapter_496 -> {
                    currentValueTitle = 495; currentValueDescriptions = 495; shortCode()
                }

                R.id.chapter_497 -> {
                    currentValueTitle = 496; currentValueDescriptions = 496; shortCode()
                }

                R.id.chapter_498 -> {
                    currentValueTitle = 497; currentValueDescriptions = 497; shortCode()
                }

                R.id.chapter_499 -> {
                    currentValueTitle = 498; currentValueDescriptions = 498; shortCode()
                }

                R.id.chapter_500 -> {
                    currentValueTitle = 499; currentValueDescriptions = 499; shortCode()
                }

                R.id.chapter_501 -> {
                    currentValueTitle = 500; currentValueDescriptions = 500; shortCode()
                }

                R.id.chapter_502 -> {
                    currentValueTitle = 501; currentValueDescriptions = 501; shortCode()
                }

                R.id.chapter_503 -> {
                    currentValueTitle = 502; currentValueDescriptions = 502; shortCode()
                }

                R.id.chapter_504 -> {
                    currentValueTitle = 503; currentValueDescriptions = 503; shortCode()
                }

                R.id.chapter_505 -> {
                    currentValueTitle = 504; currentValueDescriptions = 504; shortCode()
                }

                R.id.chapter_506 -> {
                    currentValueTitle = 505; currentValueDescriptions = 505; shortCode()
                }

                R.id.chapter_507 -> {
                    currentValueTitle = 506; currentValueDescriptions = 506; shortCode()
                }

                R.id.chapter_508 -> {
                    currentValueTitle = 507; currentValueDescriptions = 507; shortCode()
                }

                R.id.chapter_509 -> {
                    currentValueTitle = 508; currentValueDescriptions = 508; shortCode()
                }

                R.id.chapter_510 -> {
                    currentValueTitle = 509; currentValueDescriptions = 509; shortCode()
                }

                R.id.chapter_511 -> {
                    currentValueTitle = 510; currentValueDescriptions = 510; shortCode()
                }

                R.id.chapter_512 -> {
                    currentValueTitle = 511; currentValueDescriptions = 511; shortCode()
                }

                R.id.chapter_513 -> {
                    currentValueTitle = 512; currentValueDescriptions = 512; shortCode()
                }

                R.id.chapter_514 -> {
                    currentValueTitle = 513; currentValueDescriptions = 513; shortCode()
                }

                R.id.chapter_515 -> {
                    currentValueTitle = 514; currentValueDescriptions = 514; shortCode()
                }

                R.id.chapter_516 -> {
                    currentValueTitle = 515; currentValueDescriptions = 515; shortCode()
                }

                R.id.chapter_517 -> {
                    currentValueTitle = 516; currentValueDescriptions = 516; shortCode()
                }

                R.id.chapter_518 -> {
                    currentValueTitle = 517; currentValueDescriptions = 517; shortCode()
                }

                R.id.chapter_519 -> {
                    currentValueTitle = 518; currentValueDescriptions = 518; shortCode()
                }

                R.id.chapter_520 -> {
                    currentValueTitle = 519; currentValueDescriptions = 519; shortCode()
                }

                R.id.chapter_521 -> {
                    currentValueTitle = 520; currentValueDescriptions = 520; shortCode()
                }

                R.id.chapter_522 -> {
                    currentValueTitle = 521; currentValueDescriptions = 521; shortCode()
                }

                R.id.chapter_523 -> {
                    currentValueTitle = 522; currentValueDescriptions = 522; shortCode()
                }

                R.id.chapter_524 -> {
                    currentValueTitle = 523; currentValueDescriptions = 523; shortCode()
                }

                R.id.chapter_525 -> {
                    currentValueTitle = 524; currentValueDescriptions = 524; shortCode()
                }

                R.id.chapter_526 -> {
                    currentValueTitle = 525; currentValueDescriptions = 525; shortCode()
                }

                R.id.chapter_527 -> {
                    currentValueTitle = 526; currentValueDescriptions = 526; shortCode()
                }

                R.id.chapter_528 -> {
                    currentValueTitle = 527; currentValueDescriptions = 527; shortCode()
                }

                R.id.chapter_529 -> {
                    currentValueTitle = 528; currentValueDescriptions = 528; shortCode()
                }

                R.id.chapter_530 -> {
                    currentValueTitle = 529; currentValueDescriptions = 529; shortCode()
                }

                R.id.chapter_531 -> {
                    currentValueTitle = 530; currentValueDescriptions = 530; shortCode()
                }

                R.id.chapter_532 -> {
                    currentValueTitle = 531; currentValueDescriptions = 531; shortCode()
                }

                R.id.chapter_533 -> {
                    currentValueTitle = 532; currentValueDescriptions = 532; shortCode()
                }

                R.id.chapter_534 -> {
                    currentValueTitle = 533; currentValueDescriptions = 533; shortCode()
                }

                R.id.chapter_535 -> {
                    currentValueTitle = 534; currentValueDescriptions = 534; shortCode()
                }

                R.id.chapter_536 -> {
                    currentValueTitle = 535; currentValueDescriptions = 535; shortCode()
                }

                R.id.chapter_537 -> {
                    currentValueTitle = 536; currentValueDescriptions = 536; shortCode()
                }

                R.id.chapter_538 -> {
                    currentValueTitle = 537; currentValueDescriptions = 537; shortCode()
                }

                R.id.chapter_539 -> {
                    currentValueTitle = 538; currentValueDescriptions = 538; shortCode()
                }

                R.id.chapter_540 -> {
                    currentValueTitle = 539; currentValueDescriptions = 539; shortCode()
                }

                R.id.chapter_541 -> {
                    currentValueTitle = 540; currentValueDescriptions = 540; shortCode()
                }

                R.id.chapter_542 -> {
                    currentValueTitle = 541; currentValueDescriptions = 541; shortCode()
                }

                R.id.chapter_543 -> {
                    currentValueTitle = 542; currentValueDescriptions = 542; shortCode()
                }

                R.id.chapter_544 -> {
                    currentValueTitle = 543; currentValueDescriptions = 543; shortCode()
                }

                R.id.chapter_545 -> {
                    currentValueTitle = 544; currentValueDescriptions = 544; shortCode()
                }

                R.id.chapter_546 -> {
                    currentValueTitle = 545; currentValueDescriptions = 545; shortCode()
                }

                R.id.chapter_547 -> {
                    currentValueTitle = 546; currentValueDescriptions = 546; shortCode()
                }

                R.id.chapter_548 -> {
                    currentValueTitle = 547; currentValueDescriptions = 547; shortCode()
                }

                R.id.chapter_549 -> {
                    currentValueTitle = 548; currentValueDescriptions = 548; shortCode()
                }

                R.id.chapter_550 -> {
                    currentValueTitle = 549; currentValueDescriptions = 549; shortCode()
                }

                R.id.chapter_551 -> {
                    currentValueTitle = 550; currentValueDescriptions = 550; shortCode()
                }

                R.id.chapter_552 -> {
                    currentValueTitle = 551; currentValueDescriptions = 551; shortCode()
                }

                R.id.chapter_553 -> {
                    currentValueTitle = 552; currentValueDescriptions = 552; shortCode()
                }

                R.id.chapter_554 -> {
                    currentValueTitle = 553; currentValueDescriptions = 553; shortCode()
                }

                R.id.chapter_555 -> {
                    currentValueTitle = 554; currentValueDescriptions = 554; shortCode()
                }

                R.id.chapter_556 -> {
                    currentValueTitle = 555; currentValueDescriptions = 555; shortCode()
                }

                R.id.chapter_557 -> {
                    currentValueTitle = 556; currentValueDescriptions = 556; shortCode()
                }

                R.id.chapter_558 -> {
                    currentValueTitle = 557; currentValueDescriptions = 557; shortCode()
                }

                R.id.chapter_559 -> {
                    currentValueTitle = 558; currentValueDescriptions = 558; shortCode()
                }

                R.id.chapter_560 -> {
                    currentValueTitle = 559; currentValueDescriptions = 559; shortCode()
                }

                R.id.chapter_561 -> {
                    currentValueTitle = 560; currentValueDescriptions = 560; shortCode()
                }

                R.id.chapter_562 -> {
                    currentValueTitle = 561; currentValueDescriptions = 561; shortCode()
                }

                R.id.chapter_563 -> {
                    currentValueTitle = 562; currentValueDescriptions = 562; shortCode()
                }

                R.id.chapter_564 -> {
                    currentValueTitle = 563; currentValueDescriptions = 563; shortCode()
                }

                R.id.chapter_565 -> {
                    currentValueTitle = 564; currentValueDescriptions = 564; shortCode()
                }

                R.id.chapter_566 -> {
                    currentValueTitle = 565; currentValueDescriptions = 565; shortCode()
                }

                R.id.chapter_567 -> {
                    currentValueTitle = 566; currentValueDescriptions = 566; shortCode()
                }

                R.id.chapter_568 -> {
                    currentValueTitle = 567; currentValueDescriptions = 567; shortCode()
                }

                R.id.chapter_569 -> {
                    currentValueTitle = 568; currentValueDescriptions = 568; shortCode()
                }

                R.id.chapter_570 -> {
                    currentValueTitle = 569; currentValueDescriptions = 569; shortCode()
                }

                R.id.chapter_571 -> {
                    currentValueTitle = 570; currentValueDescriptions = 570; shortCode()
                }

                R.id.chapter_572 -> {
                    currentValueTitle = 571; currentValueDescriptions = 571; shortCode()
                }

                R.id.chapter_573 -> {
                    currentValueTitle = 572; currentValueDescriptions = 572; shortCode()
                }

                R.id.chapter_574 -> {
                    currentValueTitle = 573; currentValueDescriptions = 573; shortCode()
                }

                R.id.chapter_575 -> {
                    currentValueTitle = 574; currentValueDescriptions = 574; shortCode()
                }

                R.id.chapter_576 -> {
                    currentValueTitle = 575; currentValueDescriptions = 575; shortCode()
                }

                R.id.chapter_577 -> {
                    currentValueTitle = 576; currentValueDescriptions = 576; shortCode()
                }

                R.id.chapter_578 -> {
                    currentValueTitle = 577; currentValueDescriptions = 577; shortCode()
                }

                R.id.chapter_579 -> {
                    currentValueTitle = 578; currentValueDescriptions = 578; shortCode()
                }

                R.id.chapter_580 -> {
                    currentValueTitle = 579; currentValueDescriptions = 579; shortCode()
                }

                R.id.chapter_581 -> {
                    currentValueTitle = 580; currentValueDescriptions = 580; shortCode()
                }

                R.id.chapter_582 -> {
                    currentValueTitle = 581; currentValueDescriptions = 581; shortCode()
                }

                R.id.chapter_583 -> {
                    currentValueTitle = 582; currentValueDescriptions = 582; shortCode()
                }

                R.id.chapter_584 -> {
                    currentValueTitle = 583; currentValueDescriptions = 583; shortCode()
                }

                R.id.chapter_585 -> {
                    currentValueTitle = 584; currentValueDescriptions = 584; shortCode()
                }

                R.id.chapter_586 -> {
                    currentValueTitle = 585; currentValueDescriptions = 585; shortCode()
                }

                R.id.chapter_587 -> {
                    currentValueTitle = 586; currentValueDescriptions = 586; shortCode()
                }

                R.id.chapter_588 -> {
                    currentValueTitle = 587; currentValueDescriptions = 587; shortCode()
                }

                R.id.chapter_589 -> {
                    currentValueTitle = 588; currentValueDescriptions = 588; shortCode()
                }

                R.id.chapter_590 -> {
                    currentValueTitle = 589; currentValueDescriptions = 589; shortCode()
                }

                R.id.chapter_591 -> {
                    currentValueTitle = 590; currentValueDescriptions = 590; shortCode()
                }

                R.id.chapter_592 -> {
                    currentValueTitle = 591; currentValueDescriptions = 591; shortCode()
                }

                R.id.chapter_593 -> {
                    currentValueTitle = 592; currentValueDescriptions = 592; shortCode()
                }

                R.id.chapter_594 -> {
                    currentValueTitle = 593; currentValueDescriptions = 593; shortCode()
                }

                R.id.chapter_595 -> {
                    currentValueTitle = 594; currentValueDescriptions = 594; shortCode()
                }

                R.id.chapter_596 -> {
                    currentValueTitle = 595; currentValueDescriptions = 595; shortCode()
                }

                R.id.chapter_597 -> {
                    currentValueTitle = 596; currentValueDescriptions = 596; shortCode()
                }

                R.id.chapter_598 -> {
                    currentValueTitle = 597; currentValueDescriptions = 597; shortCode()
                }

                R.id.chapter_599 -> {
                    currentValueTitle = 598; currentValueDescriptions = 598; shortCode()
                }

                R.id.chapter_600 -> {
                    currentValueTitle = 599; currentValueDescriptions = 599; shortCode()
                }

                R.id.chapter_601 -> {
                    currentValueTitle = 600; currentValueDescriptions = 600; shortCode()
                }

                R.id.chapter_602 -> {
                    currentValueTitle = 601; currentValueDescriptions = 601; shortCode()
                }

                R.id.chapter_603 -> {
                    currentValueTitle = 602; currentValueDescriptions = 602; shortCode()
                }

                R.id.chapter_604 -> {
                    currentValueTitle = 603; currentValueDescriptions = 603; shortCode()
                }

                R.id.chapter_605 -> {
                    currentValueTitle = 604; currentValueDescriptions = 604; shortCode()
                }

                R.id.chapter_606 -> {
                    currentValueTitle = 605; currentValueDescriptions = 605; shortCode()
                }

                R.id.chapter_607 -> {
                    currentValueTitle = 606; currentValueDescriptions = 606; shortCode()
                }

                R.id.chapter_608 -> {
                    currentValueTitle = 607; currentValueDescriptions = 607; shortCode()
                }

                R.id.chapter_609 -> {
                    currentValueTitle = 608; currentValueDescriptions = 608; shortCode()
                }

                R.id.chapter_610 -> {
                    currentValueTitle = 609; currentValueDescriptions = 609; shortCode()
                }

                R.id.chapter_611 -> {
                    currentValueTitle = 610; currentValueDescriptions = 610; shortCode()
                }

                R.id.chapter_612 -> {
                    currentValueTitle = 611; currentValueDescriptions = 611; shortCode()
                }

                R.id.chapter_613 -> {
                    currentValueTitle = 612; currentValueDescriptions = 612; shortCode()
                }

                R.id.chapter_614 -> {
                    currentValueTitle = 613; currentValueDescriptions = 613; shortCode()
                }

                R.id.chapter_615 -> {
                    currentValueTitle = 614; currentValueDescriptions = 614; shortCode()
                }

                R.id.chapter_616 -> {
                    currentValueTitle = 615; currentValueDescriptions = 615; shortCode()
                }

                R.id.chapter_617 -> {
                    currentValueTitle = 616; currentValueDescriptions = 616; shortCode()
                }

                R.id.chapter_618 -> {
                    currentValueTitle = 617; currentValueDescriptions = 617; shortCode()
                }

                R.id.chapter_619 -> {
                    currentValueTitle = 618; currentValueDescriptions = 618; shortCode()
                }

                R.id.chapter_620 -> {
                    currentValueTitle = 619; currentValueDescriptions = 619; shortCode()
                }

                R.id.chapter_621 -> {
                    currentValueTitle = 620; currentValueDescriptions = 620; shortCode()
                }

                R.id.chapter_622 -> {
                    currentValueTitle = 621; currentValueDescriptions = 621; shortCode()
                }

                R.id.chapter_623 -> {
                    currentValueTitle = 622; currentValueDescriptions = 622; shortCode()
                }

                R.id.chapter_624 -> {
                    currentValueTitle = 623; currentValueDescriptions = 623; shortCode()
                }

                R.id.chapter_625 -> {
                    currentValueTitle = 624; currentValueDescriptions = 624; shortCode()
                }

                R.id.chapter_626 -> {
                    currentValueTitle = 625; currentValueDescriptions = 625; shortCode()
                }

                R.id.chapter_627 -> {
                    currentValueTitle = 626; currentValueDescriptions = 626; shortCode()
                }

                R.id.chapter_628 -> {
                    currentValueTitle = 627; currentValueDescriptions = 627; shortCode()
                }

                R.id.chapter_629 -> {
                    currentValueTitle = 628; currentValueDescriptions = 628; shortCode()
                }

                R.id.chapter_630 -> {
                    currentValueTitle = 629; currentValueDescriptions = 629; shortCode()
                }

                R.id.chapter_631 -> {
                    currentValueTitle = 630; currentValueDescriptions = 630; shortCode()
                }

                R.id.chapter_632 -> {
                    currentValueTitle = 631; currentValueDescriptions = 631; shortCode()
                }

                R.id.chapter_633 -> {
                    currentValueTitle = 632; currentValueDescriptions = 632; shortCode()
                }

                R.id.chapter_634 -> {
                    currentValueTitle = 633; currentValueDescriptions = 633; shortCode()
                }

                R.id.chapter_635 -> {
                    currentValueTitle = 634; currentValueDescriptions = 634; shortCode()
                }

                R.id.chapter_636 -> {
                    currentValueTitle = 635; currentValueDescriptions = 635; shortCode()
                }

                R.id.chapter_637 -> {
                    currentValueTitle = 636; currentValueDescriptions = 636; shortCode()
                }

                R.id.chapter_638 -> {
                    currentValueTitle = 637; currentValueDescriptions = 637; shortCode()
                }

                R.id.chapter_639 -> {
                    currentValueTitle = 638; currentValueDescriptions = 638; shortCode()
                }

                R.id.chapter_640 -> {
                    currentValueTitle = 639; currentValueDescriptions = 639; shortCode()
                }

                R.id.chapter_641 -> {
                    currentValueTitle = 640; currentValueDescriptions = 640; shortCode()
                }

                R.id.chapter_642 -> {
                    currentValueTitle = 641; currentValueDescriptions = 641; shortCode()
                }

                R.id.chapter_643 -> {
                    currentValueTitle = 642; currentValueDescriptions = 642; shortCode()
                }

                R.id.chapter_644 -> {
                    currentValueTitle = 643; currentValueDescriptions = 643; shortCode()
                }

                R.id.chapter_645 -> {
                    currentValueTitle = 644; currentValueDescriptions = 644; shortCode()
                }

                R.id.chapter_646 -> {
                    currentValueTitle = 645; currentValueDescriptions = 645; shortCode()
                }

                R.id.chapter_647 -> {
                    currentValueTitle = 646; currentValueDescriptions = 646; shortCode()
                }

                R.id.chapter_648 -> {
                    currentValueTitle = 647; currentValueDescriptions = 647; shortCode()
                }

                R.id.chapter_649 -> {
                    currentValueTitle = 648; currentValueDescriptions = 648; shortCode()
                }

                R.id.chapter_650 -> {
                    currentValueTitle = 649; currentValueDescriptions = 649; shortCode()
                }

                R.id.chapter_651 -> {
                    currentValueTitle = 650; currentValueDescriptions = 650; shortCode()
                }

                R.id.chapter_652 -> {
                    currentValueTitle = 651; currentValueDescriptions = 651; shortCode()
                }

                R.id.chapter_653 -> {
                    currentValueTitle = 652; currentValueDescriptions = 652; shortCode()
                }

                R.id.chapter_654 -> {
                    currentValueTitle = 653; currentValueDescriptions = 653; shortCode()
                }

                R.id.chapter_655 -> {
                    currentValueTitle = 654; currentValueDescriptions = 654; shortCode()
                }

                R.id.chapter_656 -> {
                    currentValueTitle = 655; currentValueDescriptions = 655; shortCode()
                }

                R.id.chapter_657 -> {
                    currentValueTitle = 656; currentValueDescriptions = 656; shortCode()
                }

                R.id.chapter_658 -> {
                    currentValueTitle = 657; currentValueDescriptions = 657; shortCode()
                }

                R.id.chapter_659 -> {
                    currentValueTitle = 658; currentValueDescriptions = 658; shortCode()
                }

                R.id.chapter_660 -> {
                    currentValueTitle = 659; currentValueDescriptions = 659; shortCode()
                }

                R.id.chapter_661 -> {
                    currentValueTitle = 660; currentValueDescriptions = 660; shortCode()
                }

                R.id.chapter_662 -> {
                    currentValueTitle = 661; currentValueDescriptions = 661; shortCode()
                }

                R.id.chapter_663 -> {
                    currentValueTitle = 662; currentValueDescriptions = 662; shortCode()
                }

                R.id.chapter_664 -> {
                    currentValueTitle = 663; currentValueDescriptions = 663; shortCode()
                }

                R.id.chapter_665 -> {
                    currentValueTitle = 664; currentValueDescriptions = 664; shortCode()
                }

                R.id.chapter_666 -> {
                    currentValueTitle = 665; currentValueDescriptions = 665; shortCode()
                }

                R.id.chapter_667 -> {
                    currentValueTitle = 666; currentValueDescriptions = 666; shortCode()
                }

                R.id.chapter_668 -> {
                    currentValueTitle = 667; currentValueDescriptions = 667; shortCode()
                }

                R.id.chapter_669 -> {
                    currentValueTitle = 668; currentValueDescriptions = 668; shortCode()
                }

                R.id.chapter_670 -> {
                    currentValueTitle = 669; currentValueDescriptions = 669; shortCode()
                }

                R.id.chapter_671 -> {
                    currentValueTitle = 670; currentValueDescriptions = 670; shortCode()
                }

                R.id.chapter_672 -> {
                    currentValueTitle = 671; currentValueDescriptions = 671; shortCode()
                }

                R.id.chapter_673 -> {
                    currentValueTitle = 672; currentValueDescriptions = 672; shortCode()
                }

                R.id.chapter_674 -> {
                    currentValueTitle = 673; currentValueDescriptions = 673; shortCode()
                }

                R.id.chapter_675 -> {
                    currentValueTitle = 674; currentValueDescriptions = 674; shortCode()
                }

                R.id.chapter_676 -> {
                    currentValueTitle = 675; currentValueDescriptions = 675; shortCode()
                }

                R.id.chapter_677 -> {
                    currentValueTitle = 676; currentValueDescriptions = 676; shortCode()
                }

                R.id.chapter_678 -> {
                    currentValueTitle = 677; currentValueDescriptions = 677; shortCode()
                }

                R.id.chapter_679 -> {
                    currentValueTitle = 678; currentValueDescriptions = 678; shortCode()
                }

                R.id.chapter_680 -> {
                    currentValueTitle = 679; currentValueDescriptions = 679; shortCode()
                }

                R.id.chapter_681 -> {
                    currentValueTitle = 680; currentValueDescriptions = 680; shortCode()
                }

                R.id.chapter_682 -> {
                    currentValueTitle = 681; currentValueDescriptions = 681; shortCode()
                }

                R.id.chapter_683 -> {
                    currentValueTitle = 682; currentValueDescriptions = 682; shortCode()
                }

                R.id.chapter_684 -> {
                    currentValueTitle = 683; currentValueDescriptions = 683; shortCode()
                }

                R.id.chapter_685 -> {
                    currentValueTitle = 684; currentValueDescriptions = 684; shortCode()
                }

                R.id.chapter_686 -> {
                    currentValueTitle = 685; currentValueDescriptions = 685; shortCode()
                }

                R.id.chapter_687 -> {
                    currentValueTitle = 686; currentValueDescriptions = 686; shortCode()
                }

                R.id.chapter_688 -> {
                    currentValueTitle = 687; currentValueDescriptions = 687; shortCode()
                }

                R.id.chapter_689 -> {
                    currentValueTitle = 688; currentValueDescriptions = 688; shortCode()
                }

                R.id.chapter_690 -> {
                    currentValueTitle = 689; currentValueDescriptions = 689; shortCode()
                }

                R.id.chapter_691 -> {
                    currentValueTitle = 690; currentValueDescriptions = 690; shortCode()
                }

                R.id.chapter_692 -> {
                    currentValueTitle = 691; currentValueDescriptions = 691; shortCode()
                }

                R.id.chapter_693 -> {
                    currentValueTitle = 692; currentValueDescriptions = 692; shortCode()
                }

                R.id.chapter_694 -> {
                    currentValueTitle = 693; currentValueDescriptions = 693; shortCode()
                }

                R.id.chapter_695 -> {
                    currentValueTitle = 694; currentValueDescriptions = 694; shortCode()
                }

                R.id.chapter_696 -> {
                    currentValueTitle = 695; currentValueDescriptions = 695; shortCode()
                }

                R.id.chapter_697 -> {
                    currentValueTitle = 696; currentValueDescriptions = 696; shortCode()
                }

                R.id.chapter_698 -> {
                    currentValueTitle = 697; currentValueDescriptions = 697; shortCode()
                }

                R.id.chapter_699 -> {
                    currentValueTitle = 698; currentValueDescriptions = 698; shortCode()
                }

                R.id.chapter_700 -> {
                    currentValueTitle = 699; currentValueDescriptions = 699; shortCode()
                }

                R.id.chapter_701 -> {
                    currentValueTitle = 700; currentValueDescriptions = 700; shortCode()
                }

                R.id.chapter_702 -> {
                    currentValueTitle = 701; currentValueDescriptions = 701; shortCode()
                }

                R.id.chapter_703 -> {
                    currentValueTitle = 702; currentValueDescriptions = 702; shortCode()
                }

                R.id.chapter_704 -> {
                    currentValueTitle = 703; currentValueDescriptions = 703; shortCode()
                }

                R.id.chapter_705 -> {
                    currentValueTitle = 704; currentValueDescriptions = 704; shortCode()
                }

                R.id.chapter_706 -> {
                    currentValueTitle = 705; currentValueDescriptions = 705; shortCode()
                }

                R.id.chapter_707 -> {
                    currentValueTitle = 706; currentValueDescriptions = 706; shortCode()
                }

                R.id.chapter_708 -> {
                    currentValueTitle = 707; currentValueDescriptions = 707; shortCode()
                }

                R.id.chapter_709 -> {
                    currentValueTitle = 708; currentValueDescriptions = 708; shortCode()
                }

                R.id.chapter_710 -> {
                    currentValueTitle = 709; currentValueDescriptions = 709; shortCode()
                }

                R.id.chapter_711 -> {
                    currentValueTitle = 710; currentValueDescriptions = 710; shortCode()
                }

                R.id.chapter_712 -> {
                    currentValueTitle = 711; currentValueDescriptions = 711; shortCode()
                }

                R.id.chapter_713 -> {
                    currentValueTitle = 712; currentValueDescriptions = 712; shortCode()
                }

                R.id.chapter_714 -> {
                    currentValueTitle = 713; currentValueDescriptions = 713; shortCode()
                }

                R.id.chapter_715 -> {
                    currentValueTitle = 714; currentValueDescriptions = 714; shortCode()
                }

                R.id.chapter_716 -> {
                    currentValueTitle = 715; currentValueDescriptions = 715; shortCode()
                }

                R.id.chapter_717 -> {
                    currentValueTitle = 716; currentValueDescriptions = 716; shortCode()
                }

                R.id.chapter_718 -> {
                    currentValueTitle = 717; currentValueDescriptions = 717; shortCode()
                }

                R.id.chapter_719 -> {
                    currentValueTitle = 718; currentValueDescriptions = 718; shortCode()
                }

                R.id.chapter_720 -> {
                    currentValueTitle = 719; currentValueDescriptions = 719; shortCode()
                }

                R.id.chapter_721 -> {
                    currentValueTitle = 720; currentValueDescriptions = 720; shortCode()
                }

                R.id.chapter_722 -> {
                    currentValueTitle = 721; currentValueDescriptions = 721; shortCode()
                }

                R.id.chapter_723 -> {
                    currentValueTitle = 722; currentValueDescriptions = 722; shortCode()
                }

                R.id.chapter_724 -> {
                    currentValueTitle = 723; currentValueDescriptions = 723; shortCode()
                }

                R.id.chapter_725 -> {
                    currentValueTitle = 724; currentValueDescriptions = 724; shortCode()
                }

                R.id.chapter_726 -> {
                    currentValueTitle = 725; currentValueDescriptions = 725; shortCode()
                }

                R.id.chapter_727 -> {
                    currentValueTitle = 726; currentValueDescriptions = 726; shortCode()
                }

                R.id.chapter_728 -> {
                    currentValueTitle = 727; currentValueDescriptions = 727; shortCode()
                }

                R.id.chapter_729 -> {
                    currentValueTitle = 728; currentValueDescriptions = 728; shortCode()
                }

                R.id.chapter_730 -> {
                    currentValueTitle = 729; currentValueDescriptions = 729; shortCode()
                }

                R.id.chapter_731 -> {
                    currentValueTitle = 730; currentValueDescriptions = 730; shortCode()
                }

                R.id.chapter_732 -> {
                    currentValueTitle = 731; currentValueDescriptions = 731; shortCode()
                }

                R.id.chapter_733 -> {
                    currentValueTitle = 732; currentValueDescriptions = 732; shortCode()
                }

                R.id.chapter_734 -> {
                    currentValueTitle = 733; currentValueDescriptions = 733; shortCode()
                }

                R.id.chapter_735 -> {
                    currentValueTitle = 734; currentValueDescriptions = 734; shortCode()
                }

                R.id.chapter_736 -> {
                    currentValueTitle = 735; currentValueDescriptions = 735; shortCode()
                }

                R.id.chapter_737 -> {
                    currentValueTitle = 736; currentValueDescriptions = 736; shortCode()
                }

                R.id.chapter_738 -> {
                    currentValueTitle = 737; currentValueDescriptions = 737; shortCode()
                }

                R.id.chapter_739 -> {
                    currentValueTitle = 738; currentValueDescriptions = 738; shortCode()
                }

                R.id.chapter_740 -> {
                    currentValueTitle = 739; currentValueDescriptions = 739; shortCode()
                }

                R.id.chapter_741 -> {
                    currentValueTitle = 740; currentValueDescriptions = 740; shortCode()
                }

                R.id.chapter_742 -> {
                    currentValueTitle = 741; currentValueDescriptions = 741; shortCode()
                }

                R.id.chapter_743 -> {
                    currentValueTitle = 742; currentValueDescriptions = 742; shortCode()
                }

                R.id.chapter_744 -> {
                    currentValueTitle = 743; currentValueDescriptions = 743; shortCode()
                }

                R.id.chapter_745 -> {
                    currentValueTitle = 744; currentValueDescriptions = 744; shortCode()
                }

                R.id.chapter_746 -> {
                    currentValueTitle = 745; currentValueDescriptions = 745; shortCode()
                }

                R.id.chapter_747 -> {
                    currentValueTitle = 746; currentValueDescriptions = 746; shortCode()
                }

                R.id.chapter_748 -> {
                    currentValueTitle = 747; currentValueDescriptions = 747; shortCode()
                }

                R.id.chapter_749 -> {
                    currentValueTitle = 748; currentValueDescriptions = 748; shortCode()
                }

                R.id.chapter_750 -> {
                    currentValueTitle = 749; currentValueDescriptions = 749; shortCode()
                }

                R.id.chapter_751 -> {
                    currentValueTitle = 750; currentValueDescriptions = 750; shortCode()
                }

                R.id.chapter_752 -> {
                    currentValueTitle = 751; currentValueDescriptions = 751; shortCode()
                }

                R.id.chapter_753 -> {
                    currentValueTitle = 752; currentValueDescriptions = 752; shortCode()
                }

                R.id.chapter_754 -> {
                    currentValueTitle = 753; currentValueDescriptions = 753; shortCode()
                }

                R.id.chapter_755 -> {
                    currentValueTitle = 754; currentValueDescriptions = 754; shortCode()
                }

                R.id.chapter_756 -> {
                    currentValueTitle = 755; currentValueDescriptions = 755; shortCode()
                }

                R.id.chapter_757 -> {
                    currentValueTitle = 756; currentValueDescriptions = 756; shortCode()
                }

                R.id.chapter_758 -> {
                    currentValueTitle = 757; currentValueDescriptions = 757; shortCode()
                }

                R.id.chapter_759 -> {
                    currentValueTitle = 758; currentValueDescriptions = 758; shortCode()
                }

                R.id.chapter_760 -> {
                    currentValueTitle = 759; currentValueDescriptions = 759; shortCode()
                }

                R.id.chapter_761 -> {
                    currentValueTitle = 760; currentValueDescriptions = 760; shortCode()
                }

                R.id.chapter_762 -> {
                    currentValueTitle = 761; currentValueDescriptions = 761; shortCode()
                }

                R.id.chapter_763 -> {
                    currentValueTitle = 762; currentValueDescriptions = 762; shortCode()
                }

                R.id.chapter_764 -> {
                    currentValueTitle = 763; currentValueDescriptions = 763; shortCode()
                }

                R.id.chapter_765 -> {
                    currentValueTitle = 764; currentValueDescriptions = 764; shortCode()
                }

                R.id.chapter_766 -> {
                    currentValueTitle = 765; currentValueDescriptions = 765; shortCode()
                }

                R.id.chapter_767 -> {
                    currentValueTitle = 766; currentValueDescriptions = 766; shortCode()
                }

                R.id.chapter_768 -> {
                    currentValueTitle = 767; currentValueDescriptions = 767; shortCode()
                }

                R.id.chapter_769 -> {
                    currentValueTitle = 768; currentValueDescriptions = 768; shortCode()
                }

                R.id.chapter_770 -> {
                    currentValueTitle = 769; currentValueDescriptions = 769; shortCode()
                }

                R.id.chapter_771 -> {
                    currentValueTitle = 770; currentValueDescriptions = 770; shortCode()
                }

                R.id.chapter_772 -> {
                    currentValueTitle = 771; currentValueDescriptions = 771; shortCode()
                }

                R.id.chapter_773 -> {
                    currentValueTitle = 772; currentValueDescriptions = 772; shortCode()
                }

                R.id.chapter_774 -> {
                    currentValueTitle = 773; currentValueDescriptions = 773; shortCode()
                }

                R.id.chapter_775 -> {
                    currentValueTitle = 774; currentValueDescriptions = 774; shortCode()
                }

                R.id.chapter_776 -> {
                    currentValueTitle = 775; currentValueDescriptions = 775; shortCode()
                }

                R.id.chapter_777 -> {
                    currentValueTitle = 776; currentValueDescriptions = 776; shortCode()
                }

                R.id.chapter_778 -> {
                    currentValueTitle = 777; currentValueDescriptions = 777; shortCode()
                }

                R.id.chapter_779 -> {
                    currentValueTitle = 778; currentValueDescriptions = 778; shortCode()
                }

                R.id.chapter_780 -> {
                    currentValueTitle = 779; currentValueDescriptions = 779; shortCode()
                }

                R.id.chapter_781 -> {
                    currentValueTitle = 780; currentValueDescriptions = 780; shortCode()
                }

                R.id.chapter_782 -> {
                    currentValueTitle = 781; currentValueDescriptions = 781; shortCode()
                }

                R.id.chapter_783 -> {
                    currentValueTitle = 782; currentValueDescriptions = 782; shortCode()
                }

                R.id.chapter_784 -> {
                    currentValueTitle = 783; currentValueDescriptions = 783; shortCode()
                }

                R.id.chapter_785 -> {
                    currentValueTitle = 784; currentValueDescriptions = 784; shortCode()
                }

                R.id.chapter_786 -> {
                    currentValueTitle = 785; currentValueDescriptions = 785; shortCode()
                }

                R.id.chapter_787 -> {
                    currentValueTitle = 786; currentValueDescriptions = 786; shortCode()
                }

                R.id.chapter_788 -> {
                    currentValueTitle = 787; currentValueDescriptions = 787; shortCode()
                }

                R.id.chapter_789 -> {
                    currentValueTitle = 788; currentValueDescriptions = 788; shortCode()
                }

                R.id.chapter_790 -> {
                    currentValueTitle = 789; currentValueDescriptions = 789; shortCode()
                }

                R.id.chapter_791 -> {
                    currentValueTitle = 790; currentValueDescriptions = 790; shortCode()
                }

                R.id.chapter_792 -> {
                    currentValueTitle = 791; currentValueDescriptions = 791; shortCode()
                }

                R.id.chapter_793 -> {
                    currentValueTitle = 792; currentValueDescriptions = 792; shortCode()
                }

                R.id.chapter_794 -> {
                    currentValueTitle = 793; currentValueDescriptions = 793; shortCode()
                }

                R.id.chapter_795 -> {
                    currentValueTitle = 794; currentValueDescriptions = 794; shortCode()
                }

                R.id.chapter_796 -> {
                    currentValueTitle = 795; currentValueDescriptions = 795; shortCode()
                }

                R.id.chapter_797 -> {
                    currentValueTitle = 796; currentValueDescriptions = 796; shortCode()
                }

                R.id.chapter_798 -> {
                    currentValueTitle = 797; currentValueDescriptions = 797; shortCode()
                }

                R.id.chapter_799 -> {
                    currentValueTitle = 798; currentValueDescriptions = 798; shortCode()
                }

                R.id.chapter_800 -> {
                    currentValueTitle = 799; currentValueDescriptions = 799; shortCode()
                }

                R.id.chapter_801 -> {
                    currentValueTitle = 800; currentValueDescriptions = 800; shortCode()
                }

                R.id.chapter_802 -> {
                    currentValueTitle = 801; currentValueDescriptions = 801; shortCode()
                }

                R.id.chapter_803 -> {
                    currentValueTitle = 802; currentValueDescriptions = 802; shortCode()
                }

                R.id.chapter_804 -> {
                    currentValueTitle = 803; currentValueDescriptions = 803; shortCode()
                }

                R.id.chapter_805 -> {
                    currentValueTitle = 804; currentValueDescriptions = 804; shortCode()
                }

                R.id.chapter_806 -> {
                    currentValueTitle = 805; currentValueDescriptions = 805; shortCode()
                }

                R.id.chapter_807 -> {
                    currentValueTitle = 806; currentValueDescriptions = 806; shortCode()
                }

                R.id.chapter_808 -> {
                    currentValueTitle = 807; currentValueDescriptions = 807; shortCode()
                }

                R.id.chapter_809 -> {
                    currentValueTitle = 808; currentValueDescriptions = 808; shortCode()
                }

                R.id.chapter_810 -> {
                    currentValueTitle = 809; currentValueDescriptions = 809; shortCode()
                }

                R.id.chapter_811 -> {
                    currentValueTitle = 810; currentValueDescriptions = 810; shortCode()
                }

                R.id.chapter_812 -> {
                    currentValueTitle = 811; currentValueDescriptions = 811; shortCode()
                }

                R.id.chapter_813 -> {
                    currentValueTitle = 812; currentValueDescriptions = 812; shortCode()
                }

                R.id.chapter_814 -> {
                    currentValueTitle = 813; currentValueDescriptions = 813; shortCode()
                }

                R.id.chapter_815 -> {
                    currentValueTitle = 814; currentValueDescriptions = 814; shortCode()
                }

                R.id.chapter_816 -> {
                    currentValueTitle = 815; currentValueDescriptions = 815; shortCode()
                }

                R.id.chapter_817 -> {
                    currentValueTitle = 816; currentValueDescriptions = 816; shortCode()
                }

                R.id.chapter_818 -> {
                    currentValueTitle = 817; currentValueDescriptions = 817; shortCode()
                }

                R.id.chapter_819 -> {
                    currentValueTitle = 818; currentValueDescriptions = 818; shortCode()
                }

                R.id.chapter_820 -> {
                    currentValueTitle = 819; currentValueDescriptions = 819; shortCode()
                }

                R.id.chapter_821 -> {
                    currentValueTitle = 820; currentValueDescriptions = 820; shortCode()
                }

                R.id.chapter_822 -> {
                    currentValueTitle = 821; currentValueDescriptions = 821; shortCode()
                }

                R.id.chapter_823 -> {
                    currentValueTitle = 822; currentValueDescriptions = 822; shortCode()
                }

                R.id.chapter_824 -> {
                    currentValueTitle = 823; currentValueDescriptions = 823; shortCode()
                }

                R.id.chapter_825 -> {
                    currentValueTitle = 824; currentValueDescriptions = 824; shortCode()
                }

                R.id.chapter_826 -> {
                    currentValueTitle = 825; currentValueDescriptions = 825; shortCode()
                }

                R.id.chapter_827 -> {
                    currentValueTitle = 826; currentValueDescriptions = 826; shortCode()
                }

                R.id.chapter_828 -> {
                    currentValueTitle = 827; currentValueDescriptions = 827; shortCode()
                }

                R.id.chapter_829 -> {
                    currentValueTitle = 828; currentValueDescriptions = 828; shortCode()
                }

                R.id.chapter_830 -> {
                    currentValueTitle = 829; currentValueDescriptions = 829; shortCode()
                }

                R.id.chapter_831 -> {
                    currentValueTitle = 830; currentValueDescriptions = 830; shortCode()
                }

                R.id.chapter_832 -> {
                    currentValueTitle = 831; currentValueDescriptions = 831; shortCode()
                }

                R.id.chapter_833 -> {
                    currentValueTitle = 832; currentValueDescriptions = 832; shortCode()
                }

                R.id.chapter_834 -> {
                    currentValueTitle = 833; currentValueDescriptions = 833; shortCode()
                }

                R.id.chapter_835 -> {
                    currentValueTitle = 834; currentValueDescriptions = 834; shortCode()
                }

                R.id.chapter_836 -> {
                    currentValueTitle = 835; currentValueDescriptions = 835; shortCode()
                }

                R.id.chapter_837 -> {
                    currentValueTitle = 836; currentValueDescriptions = 836; shortCode()
                }

                R.id.chapter_838 -> {
                    currentValueTitle = 837; currentValueDescriptions = 837; shortCode()
                }

                R.id.chapter_839 -> {
                    currentValueTitle = 838; currentValueDescriptions = 838; shortCode()
                }

                R.id.chapter_840 -> {
                    currentValueTitle = 839; currentValueDescriptions = 839; shortCode()
                }

                R.id.chapter_841 -> {
                    currentValueTitle = 840; currentValueDescriptions = 840; shortCode()
                }

                R.id.chapter_842 -> {
                    currentValueTitle = 841; currentValueDescriptions = 841; shortCode()
                }

                R.id.chapter_843 -> {
                    currentValueTitle = 842; currentValueDescriptions = 842; shortCode()
                }

                R.id.chapter_844 -> {
                    currentValueTitle = 843; currentValueDescriptions = 843; shortCode()
                }

                R.id.chapter_845 -> {
                    currentValueTitle = 844; currentValueDescriptions = 844; shortCode()
                }

                R.id.chapter_846 -> {
                    currentValueTitle = 845; currentValueDescriptions = 845; shortCode()
                }

                R.id.chapter_847 -> {
                    currentValueTitle = 846; currentValueDescriptions = 846; shortCode()
                }

                R.id.chapter_848 -> {
                    currentValueTitle = 847; currentValueDescriptions = 847; shortCode()
                }

                R.id.chapter_849 -> {
                    currentValueTitle = 848; currentValueDescriptions = 848; shortCode()
                }

                R.id.chapter_850 -> {
                    currentValueTitle = 849; currentValueDescriptions = 849; shortCode()
                }

                R.id.chapter_851 -> {
                    currentValueTitle = 850; currentValueDescriptions = 850; shortCode()
                }

                R.id.chapter_852 -> {
                    currentValueTitle = 851; currentValueDescriptions = 851; shortCode()
                }

                R.id.chapter_853 -> {
                    currentValueTitle = 852; currentValueDescriptions = 852; shortCode()
                }

                R.id.chapter_854 -> {
                    currentValueTitle = 853; currentValueDescriptions = 853; shortCode()
                }

                R.id.chapter_855 -> {
                    currentValueTitle = 854; currentValueDescriptions = 854; shortCode()
                }

                R.id.chapter_856 -> {
                    currentValueTitle = 855; currentValueDescriptions = 855; shortCode()
                }

                R.id.chapter_857 -> {
                    currentValueTitle = 856; currentValueDescriptions = 856; shortCode()
                }

                R.id.chapter_858 -> {
                    currentValueTitle = 857; currentValueDescriptions = 857; shortCode()
                }

                R.id.chapter_859 -> {
                    currentValueTitle = 858; currentValueDescriptions = 858; shortCode()
                }

                R.id.chapter_860 -> {
                    currentValueTitle = 859; currentValueDescriptions = 859; shortCode()
                }

                R.id.chapter_861 -> {
                    currentValueTitle = 860; currentValueDescriptions = 860; shortCode()
                }

                R.id.chapter_862 -> {
                    currentValueTitle = 861; currentValueDescriptions = 861; shortCode()
                }

                R.id.chapter_863 -> {
                    currentValueTitle = 862; currentValueDescriptions = 862; shortCode()
                }

                R.id.chapter_864 -> {
                    currentValueTitle = 863; currentValueDescriptions = 863; shortCode()
                }

                R.id.chapter_865 -> {
                    currentValueTitle = 864; currentValueDescriptions = 864; shortCode()
                }

                R.id.chapter_866 -> {
                    currentValueTitle = 865; currentValueDescriptions = 865; shortCode()
                }

                R.id.chapter_867 -> {
                    currentValueTitle = 866; currentValueDescriptions = 866; shortCode()
                }

                R.id.chapter_868 -> {
                    currentValueTitle = 867; currentValueDescriptions = 867; shortCode()
                }

                R.id.chapter_869 -> {
                    currentValueTitle = 868; currentValueDescriptions = 868; shortCode()
                }

                R.id.chapter_870 -> {
                    currentValueTitle = 869; currentValueDescriptions = 869; shortCode()
                }

                R.id.chapter_871 -> {
                    currentValueTitle = 870; currentValueDescriptions = 870; shortCode()
                }

                R.id.chapter_872 -> {
                    currentValueTitle = 871; currentValueDescriptions = 871; shortCode()
                }

                R.id.chapter_873 -> {
                    currentValueTitle = 872; currentValueDescriptions = 872; shortCode()
                }

                R.id.chapter_874 -> {
                    currentValueTitle = 873; currentValueDescriptions = 873; shortCode()
                }

                R.id.chapter_875 -> {
                    currentValueTitle = 874; currentValueDescriptions = 874; shortCode()
                }

                R.id.chapter_876 -> {
                    currentValueTitle = 875; currentValueDescriptions = 875; shortCode()
                }

                R.id.chapter_877 -> {
                    currentValueTitle = 876; currentValueDescriptions = 876; shortCode()
                }

                R.id.chapter_878 -> {
                    currentValueTitle = 877; currentValueDescriptions = 877; shortCode()
                }

                R.id.chapter_879 -> {
                    currentValueTitle = 878; currentValueDescriptions = 878; shortCode()
                }

                R.id.chapter_880 -> {
                    currentValueTitle = 879; currentValueDescriptions = 879; shortCode()
                }

                R.id.chapter_881 -> {
                    currentValueTitle = 880; currentValueDescriptions = 880; shortCode()
                }

                R.id.chapter_882 -> {
                    currentValueTitle = 881; currentValueDescriptions = 881; shortCode()
                }

                R.id.chapter_883 -> {
                    currentValueTitle = 882; currentValueDescriptions = 882; shortCode()
                }

                R.id.chapter_884 -> {
                    currentValueTitle = 883; currentValueDescriptions = 883; shortCode()
                }

                R.id.chapter_885 -> {
                    currentValueTitle = 884; currentValueDescriptions = 884; shortCode()
                }

                R.id.chapter_886 -> {
                    currentValueTitle = 885; currentValueDescriptions = 885; shortCode()
                }

                R.id.chapter_887 -> {
                    currentValueTitle = 886; currentValueDescriptions = 886; shortCode()
                }

                R.id.chapter_888 -> {
                    currentValueTitle = 887; currentValueDescriptions = 887; shortCode()
                }

                R.id.chapter_889 -> {
                    currentValueTitle = 888; currentValueDescriptions = 888; shortCode()
                }

                R.id.chapter_890 -> {
                    currentValueTitle = 889; currentValueDescriptions = 889; shortCode()
                }

                R.id.chapter_891 -> {
                    currentValueTitle = 890; currentValueDescriptions = 890; shortCode()
                }

                R.id.chapter_892 -> {
                    currentValueTitle = 891; currentValueDescriptions = 891; shortCode()
                }

                R.id.chapter_893 -> {
                    currentValueTitle = 892; currentValueDescriptions = 892; shortCode()
                }

                R.id.chapter_894 -> {
                    currentValueTitle = 893; currentValueDescriptions = 893; shortCode()
                }

                R.id.chapter_895 -> {
                    currentValueTitle = 894; currentValueDescriptions = 894; shortCode()
                }

                R.id.chapter_896 -> {
                    currentValueTitle = 895; currentValueDescriptions = 895; shortCode()
                }

                R.id.chapter_897 -> {
                    currentValueTitle = 896; currentValueDescriptions = 896; shortCode()
                }

                R.id.chapter_898 -> {
                    currentValueTitle = 897; currentValueDescriptions = 897; shortCode()
                }

                R.id.chapter_899 -> {
                    currentValueTitle = 898; currentValueDescriptions = 898; shortCode()
                }

                R.id.chapter_900 -> {
                    currentValueTitle = 899; currentValueDescriptions = 899; shortCode()
                }

                R.id.chapter_901 -> {
                    currentValueTitle = 900; currentValueDescriptions = 900; shortCode()
                }

                R.id.chapter_902 -> {
                    currentValueTitle = 901; currentValueDescriptions = 901; shortCode()
                }

                R.id.chapter_903 -> {
                    currentValueTitle = 902; currentValueDescriptions = 902; shortCode()
                }

                R.id.chapter_904 -> {
                    currentValueTitle = 903; currentValueDescriptions = 903; shortCode()
                }

                R.id.chapter_905 -> {
                    currentValueTitle = 904; currentValueDescriptions = 904; shortCode()
                }

                R.id.chapter_906 -> {
                    currentValueTitle = 905; currentValueDescriptions = 905; shortCode()
                }

                R.id.chapter_907 -> {
                    currentValueTitle = 906; currentValueDescriptions = 906; shortCode()
                }

                R.id.chapter_908 -> {
                    currentValueTitle = 907; currentValueDescriptions = 907; shortCode()
                }

                R.id.chapter_909 -> {
                    currentValueTitle = 908; currentValueDescriptions = 908; shortCode()
                }

                R.id.chapter_910 -> {
                    currentValueTitle = 909; currentValueDescriptions = 909; shortCode()
                }

                R.id.chapter_911 -> {
                    currentValueTitle = 910; currentValueDescriptions = 910; shortCode()
                }

                R.id.chapter_912 -> {
                    currentValueTitle = 911; currentValueDescriptions = 911; shortCode()
                }

                R.id.chapter_913 -> {
                    currentValueTitle = 912; currentValueDescriptions = 912; shortCode()
                }

                R.id.chapter_914 -> {
                    currentValueTitle = 913; currentValueDescriptions = 913; shortCode()
                }

                R.id.chapter_915 -> {
                    currentValueTitle = 914; currentValueDescriptions = 914; shortCode()
                }

                R.id.chapter_916 -> {
                    currentValueTitle = 915; currentValueDescriptions = 915; shortCode()
                }

                R.id.chapter_917 -> {
                    currentValueTitle = 916; currentValueDescriptions = 916; shortCode()
                }

                R.id.chapter_918 -> {
                    currentValueTitle = 917; currentValueDescriptions = 917; shortCode()
                }

                R.id.chapter_919 -> {
                    currentValueTitle = 918; currentValueDescriptions = 918; shortCode()
                }

                R.id.chapter_920 -> {
                    currentValueTitle = 919; currentValueDescriptions = 919; shortCode()
                }

                R.id.chapter_921 -> {
                    currentValueTitle = 920; currentValueDescriptions = 920; shortCode()
                }

                R.id.chapter_922 -> {
                    currentValueTitle = 921; currentValueDescriptions = 921; shortCode()
                }

                R.id.chapter_923 -> {
                    currentValueTitle = 922; currentValueDescriptions = 922; shortCode()
                }

                R.id.chapter_924 -> {
                    currentValueTitle = 923; currentValueDescriptions = 923; shortCode()
                }

                R.id.chapter_925 -> {
                    currentValueTitle = 924; currentValueDescriptions = 924; shortCode()
                }

                R.id.chapter_926 -> {
                    currentValueTitle = 925; currentValueDescriptions = 925; shortCode()
                }

                R.id.chapter_927 -> {
                    currentValueTitle = 926; currentValueDescriptions = 926; shortCode()
                }

                R.id.chapter_928 -> {
                    currentValueTitle = 927; currentValueDescriptions = 927; shortCode()
                }

                R.id.chapter_929 -> {
                    currentValueTitle = 928; currentValueDescriptions = 928; shortCode()
                }

                R.id.chapter_930 -> {
                    currentValueTitle = 929; currentValueDescriptions = 929; shortCode()
                }

                R.id.chapter_931 -> {
                    currentValueTitle = 930; currentValueDescriptions = 930; shortCode()
                }

                R.id.chapter_932 -> {
                    currentValueTitle = 931; currentValueDescriptions = 931; shortCode()
                }

                R.id.chapter_933 -> {
                    currentValueTitle = 932; currentValueDescriptions = 932; shortCode()
                }

                R.id.chapter_934 -> {
                    currentValueTitle = 933; currentValueDescriptions = 933; shortCode()
                }

                R.id.chapter_935 -> {
                    currentValueTitle = 934; currentValueDescriptions = 934; shortCode()
                }

                R.id.chapter_936 -> {
                    currentValueTitle = 935; currentValueDescriptions = 935; shortCode()
                }

                R.id.chapter_937 -> {
                    currentValueTitle = 936; currentValueDescriptions = 936; shortCode()
                }

                R.id.chapter_938 -> {
                    currentValueTitle = 937; currentValueDescriptions = 937; shortCode()
                }

                R.id.chapter_939 -> {
                    currentValueTitle = 938; currentValueDescriptions = 938; shortCode()
                }

                R.id.chapter_940 -> {
                    currentValueTitle = 939; currentValueDescriptions = 939; shortCode()
                }

                R.id.chapter_941 -> {
                    currentValueTitle = 940; currentValueDescriptions = 940; shortCode()
                }

                R.id.chapter_942 -> {
                    currentValueTitle = 941; currentValueDescriptions = 941; shortCode()
                }

                R.id.chapter_943 -> {
                    currentValueTitle = 942; currentValueDescriptions = 942; shortCode()
                }

                R.id.chapter_944 -> {
                    currentValueTitle = 943; currentValueDescriptions = 943; shortCode()
                }

                R.id.chapter_945 -> {
                    currentValueTitle = 944; currentValueDescriptions = 944; shortCode()
                }

                R.id.chapter_946 -> {
                    currentValueTitle = 945; currentValueDescriptions = 945; shortCode()
                }

                R.id.chapter_947 -> {
                    currentValueTitle = 946; currentValueDescriptions = 946; shortCode()
                }

                R.id.chapter_948 -> {
                    currentValueTitle = 947; currentValueDescriptions = 947; shortCode()
                }

                R.id.chapter_949 -> {
                    currentValueTitle = 948; currentValueDescriptions = 948; shortCode()
                }

                R.id.chapter_950 -> {
                    currentValueTitle = 949; currentValueDescriptions = 949; shortCode()
                }

                R.id.chapter_951 -> {
                    currentValueTitle = 950; currentValueDescriptions = 950; shortCode()
                }

                R.id.chapter_952 -> {
                    currentValueTitle = 951; currentValueDescriptions = 951; shortCode()
                }

                R.id.chapter_953 -> {
                    currentValueTitle = 952; currentValueDescriptions = 952; shortCode()
                }

                R.id.chapter_954 -> {
                    currentValueTitle = 953; currentValueDescriptions = 953; shortCode()
                }

                R.id.chapter_955 -> {
                    currentValueTitle = 954; currentValueDescriptions = 954; shortCode()
                }

                R.id.chapter_956 -> {
                    currentValueTitle = 955; currentValueDescriptions = 955; shortCode()
                }

                R.id.chapter_957 -> {
                    currentValueTitle = 956; currentValueDescriptions = 956; shortCode()
                }

                R.id.chapter_958 -> {
                    currentValueTitle = 957; currentValueDescriptions = 957; shortCode()
                }

                R.id.chapter_959 -> {
                    currentValueTitle = 958; currentValueDescriptions = 958; shortCode()
                }

                R.id.chapter_960 -> {
                    currentValueTitle = 959; currentValueDescriptions = 959; shortCode()
                }

                R.id.chapter_961 -> {
                    currentValueTitle = 960; currentValueDescriptions = 960; shortCode()
                }

                R.id.chapter_962 -> {
                    currentValueTitle = 961; currentValueDescriptions = 961; shortCode()
                }

                R.id.chapter_963 -> {
                    currentValueTitle = 962; currentValueDescriptions = 962; shortCode()
                }

                R.id.chapter_964 -> {
                    currentValueTitle = 963; currentValueDescriptions = 963; shortCode()
                }

                R.id.chapter_965 -> {
                    currentValueTitle = 964; currentValueDescriptions = 964; shortCode()
                }

                R.id.chapter_966 -> {
                    currentValueTitle = 965; currentValueDescriptions = 965; shortCode()
                }

                R.id.chapter_967 -> {
                    currentValueTitle = 966; currentValueDescriptions = 966; shortCode()
                }

                R.id.chapter_968 -> {
                    currentValueTitle = 967; currentValueDescriptions = 967; shortCode()
                }

                R.id.chapter_969 -> {
                    currentValueTitle = 968; currentValueDescriptions = 968; shortCode()
                }

                R.id.chapter_970 -> {
                    currentValueTitle = 969; currentValueDescriptions = 969; shortCode()
                }

                R.id.chapter_971 -> {
                    currentValueTitle = 970; currentValueDescriptions = 970; shortCode()
                }

                R.id.chapter_972 -> {
                    currentValueTitle = 971; currentValueDescriptions = 971; shortCode()
                }

                R.id.chapter_973 -> {
                    currentValueTitle = 972; currentValueDescriptions = 972; shortCode()
                }

                R.id.chapter_974 -> {
                    currentValueTitle = 973; currentValueDescriptions = 973; shortCode()
                }

                R.id.chapter_975 -> {
                    currentValueTitle = 974; currentValueDescriptions = 974; shortCode()
                }

                R.id.chapter_976 -> {
                    currentValueTitle = 975; currentValueDescriptions = 975; shortCode()
                }

                R.id.chapter_977 -> {
                    currentValueTitle = 976; currentValueDescriptions = 976; shortCode()
                }

                R.id.chapter_978 -> {
                    currentValueTitle = 977; currentValueDescriptions = 977; shortCode()
                }

                R.id.chapter_979 -> {
                    currentValueTitle = 978; currentValueDescriptions = 978; shortCode()
                }

                R.id.chapter_980 -> {
                    currentValueTitle = 979; currentValueDescriptions = 979; shortCode()
                }

                R.id.chapter_981 -> {
                    currentValueTitle = 980; currentValueDescriptions = 980; shortCode()
                }

                R.id.chapter_982 -> {
                    currentValueTitle = 981; currentValueDescriptions = 981; shortCode()
                }

                R.id.chapter_983 -> {
                    currentValueTitle = 982; currentValueDescriptions = 982; shortCode()
                }

                R.id.chapter_984 -> {
                    currentValueTitle = 983; currentValueDescriptions = 983; shortCode()
                }

                R.id.chapter_985 -> {
                    currentValueTitle = 984; currentValueDescriptions = 984; shortCode()
                }

                R.id.chapter_986 -> {
                    currentValueTitle = 985; currentValueDescriptions = 985; shortCode()
                }

                R.id.chapter_987 -> {
                    currentValueTitle = 986; currentValueDescriptions = 986; shortCode()
                }

                R.id.chapter_988 -> {
                    currentValueTitle = 987; currentValueDescriptions = 987; shortCode()
                }

                R.id.chapter_989 -> {
                    currentValueTitle = 988; currentValueDescriptions = 988; shortCode()
                }

                R.id.chapter_990 -> {
                    currentValueTitle = 989; currentValueDescriptions = 989; shortCode()
                }

                R.id.chapter_991 -> {
                    currentValueTitle = 990; currentValueDescriptions = 990; shortCode()
                }

                R.id.chapter_992 -> {
                    currentValueTitle = 991; currentValueDescriptions = 991; shortCode()
                }

                R.id.chapter_993 -> {
                    currentValueTitle = 992; currentValueDescriptions = 992; shortCode()
                }

                R.id.chapter_994 -> {
                    currentValueTitle = 993; currentValueDescriptions = 993; shortCode()
                }

                R.id.chapter_995 -> {
                    currentValueTitle = 994; currentValueDescriptions = 994; shortCode()
                }

                R.id.chapter_996 -> {
                    currentValueTitle = 995; currentValueDescriptions = 995; shortCode()
                }

                R.id.chapter_997 -> {
                    currentValueTitle = 996; currentValueDescriptions = 996; shortCode()
                }

                R.id.chapter_998 -> {
                    currentValueTitle = 997; currentValueDescriptions = 997; shortCode()
                }

                R.id.chapter_999 -> {
                    currentValueTitle = 998; currentValueDescriptions = 998; shortCode()
                }

                R.id.chapter_1000 -> {
                    currentValueTitle = 999; currentValueDescriptions = 999; shortCode()
                }

                R.id.chapter_1001 -> {
                    currentValueTitle = 1000; currentValueDescriptions = 1000; shortCode()
                }

                R.id.chapter_1002 -> {
                    currentValueTitle = 1001; currentValueDescriptions = 1001; shortCode()
                }

                R.id.chapter_1003 -> {
                    currentValueTitle = 1002; currentValueDescriptions = 1002; shortCode()
                }

                R.id.chapter_1004 -> {
                    currentValueTitle = 1003; currentValueDescriptions = 1003; shortCode()
                }

                R.id.chapter_1005 -> {
                    currentValueTitle = 1004; currentValueDescriptions = 1004; shortCode()
                }

                R.id.chapter_1006 -> {
                    currentValueTitle = 1005; currentValueDescriptions = 1005; shortCode()
                }

                R.id.chapter_1007 -> {
                    currentValueTitle = 1006; currentValueDescriptions = 1006; shortCode()
                }

                R.id.chapter_1008 -> {
                    currentValueTitle = 1007; currentValueDescriptions = 1007; shortCode()
                }

                R.id.chapter_1009 -> {
                    currentValueTitle = 1008; currentValueDescriptions = 1008; shortCode()
                }

                R.id.chapter_1010 -> {
                    currentValueTitle = 1009; currentValueDescriptions = 1009; shortCode()
                }

                R.id.chapter_1011 -> {
                    currentValueTitle = 1010; currentValueDescriptions = 1010; shortCode()
                }

                R.id.chapter_1012 -> {
                    currentValueTitle = 1011; currentValueDescriptions = 1011; shortCode()
                }

                R.id.chapter_1013 -> {
                    currentValueTitle = 1012; currentValueDescriptions = 1012; shortCode()
                }

                R.id.chapter_1014 -> {
                    currentValueTitle = 1013; currentValueDescriptions = 1013; shortCode()
                }

                R.id.chapter_1015 -> {
                    currentValueTitle = 1014; currentValueDescriptions = 1014; shortCode()
                }

                R.id.chapter_1016 -> {
                    currentValueTitle = 1015; currentValueDescriptions = 1015; shortCode()
                }

                R.id.chapter_1017 -> {
                    currentValueTitle = 1016; currentValueDescriptions = 1016; shortCode()
                }

                R.id.chapter_1018 -> {
                    currentValueTitle = 1017; currentValueDescriptions = 1017; shortCode()
                }

                R.id.chapter_1019 -> {
                    currentValueTitle = 1018; currentValueDescriptions = 1018; shortCode()
                }

                R.id.chapter_1020 -> {
                    currentValueTitle = 1019; currentValueDescriptions = 1019; shortCode()
                }

                R.id.chapter_1021 -> {
                    currentValueTitle = 1020; currentValueDescriptions = 1020; shortCode()
                }

                R.id.chapter_1022 -> {
                    currentValueTitle = 1021; currentValueDescriptions = 1021; shortCode()
                }

                R.id.chapter_1023 -> {
                    currentValueTitle = 1022; currentValueDescriptions = 1022; shortCode()
                }

                R.id.chapter_1024 -> {
                    currentValueTitle = 1023; currentValueDescriptions = 1023; shortCode()
                }

                R.id.chapter_1025 -> {
                    currentValueTitle = 1024; currentValueDescriptions = 1024; shortCode()
                }

                R.id.chapter_1026 -> {
                    currentValueTitle = 1025; currentValueDescriptions = 1025; shortCode()
                }

                R.id.chapter_1027 -> {
                    currentValueTitle = 1026; currentValueDescriptions = 1026; shortCode()
                }

                R.id.chapter_1028 -> {
                    currentValueTitle = 1027; currentValueDescriptions = 1027; shortCode()
                }

                R.id.chapter_1029 -> {
                    currentValueTitle = 1028; currentValueDescriptions = 1028; shortCode()
                }

                R.id.chapter_1020 -> {
                    currentValueTitle = 1029; currentValueDescriptions = 1029; shortCode()
                }

                R.id.chapter_1031 -> {
                    currentValueTitle = 1030; currentValueDescriptions = 1030; shortCode()
                }

                R.id.chapter_1032 -> {
                    currentValueTitle = 1031; currentValueDescriptions = 1031; shortCode()
                }

                R.id.chapter_1033 -> {
                    currentValueTitle = 1032; currentValueDescriptions = 1032; shortCode()
                }

                R.id.chapter_1034 -> {
                    currentValueTitle = 1033; currentValueDescriptions = 1033; shortCode()
                }

                R.id.chapter_1035 -> {
                    currentValueTitle = 1034; currentValueDescriptions = 1034; shortCode()
                }

                R.id.chapter_1036 -> {
                    currentValueTitle = 1035; currentValueDescriptions = 1035; shortCode()
                }

                R.id.chapter_1037 -> {
                    currentValueTitle = 1036; currentValueDescriptions = 1036; shortCode()
                }

                R.id.chapter_1038 -> {
                    currentValueTitle = 1037; currentValueDescriptions = 1037; shortCode()
                }

                R.id.chapter_1039 -> {
                    currentValueTitle = 1038; currentValueDescriptions = 1038; shortCode()
                }

                R.id.chapter_1040 -> {
                    currentValueTitle = 1039; currentValueDescriptions = 1039; shortCode()
                }

                R.id.chapter_1041 -> {
                    currentValueTitle = 1040; currentValueDescriptions = 1040; shortCode()
                }

                R.id.chapter_1042 -> {
                    currentValueTitle = 1041; currentValueDescriptions = 1041; shortCode()
                }

                R.id.chapter_1043 -> {
                    currentValueTitle = 1042; currentValueDescriptions = 1042; shortCode()
                }

                R.id.chapter_1044 -> {
                    currentValueTitle = 1043; currentValueDescriptions = 1043; shortCode()
                }

                R.id.chapter_1045 -> {
                    currentValueTitle = 1044; currentValueDescriptions = 1044; shortCode()
                }

                R.id.chapter_1046 -> {
                    currentValueTitle = 1045; currentValueDescriptions = 1045; shortCode()
                }

                R.id.chapter_1047 -> {
                    currentValueTitle = 1046; currentValueDescriptions = 1046; shortCode()
                }

                R.id.chapter_1048 -> {
                    currentValueTitle = 1047; currentValueDescriptions = 1047; shortCode()
                }

                R.id.chapter_1049 -> {
                    currentValueTitle = 1048; currentValueDescriptions = 1048; shortCode()
                }

                R.id.chapter_1050 -> {
                    currentValueTitle = 1049; currentValueDescriptions = 1049; shortCode()
                }

                R.id.chapter_1051 -> {
                    currentValueTitle = 1050; currentValueDescriptions = 1050; shortCode()
                }

                R.id.chapter_1052 -> {
                    currentValueTitle = 1051; currentValueDescriptions = 1051; shortCode()
                }

                R.id.chapter_1053 -> {
                    currentValueTitle = 1052; currentValueDescriptions = 1052; shortCode()
                }

                R.id.chapter_1054 -> {
                    currentValueTitle = 1053; currentValueDescriptions = 1053; shortCode()
                }

                R.id.chapter_1055 -> {
                    currentValueTitle = 1054; currentValueDescriptions = 1054; shortCode()
                }

                R.id.chapter_1056 -> {
                    currentValueTitle = 1055; currentValueDescriptions = 1055; shortCode()
                }

                R.id.chapter_1057 -> {
                    currentValueTitle = 1056; currentValueDescriptions = 1056; shortCode()
                }

                R.id.chapter_1058 -> {
                    currentValueTitle = 1057; currentValueDescriptions = 1057; shortCode()
                }

                R.id.chapter_1059 -> {
                    currentValueTitle = 1058; currentValueDescriptions = 1058; shortCode()
                }

                R.id.chapter_1060 -> {
                    currentValueTitle = 1059; currentValueDescriptions = 1059; shortCode()
                }

                R.id.chapter_1061 -> {
                    currentValueTitle = 1060; currentValueDescriptions = 1060; shortCode()
                }

                R.id.chapter_1062 -> {
                    currentValueTitle = 1061; currentValueDescriptions = 1061; shortCode()
                }

                R.id.chapter_1063 -> {
                    currentValueTitle = 1062; currentValueDescriptions = 1062; shortCode()
                }

                R.id.chapter_1064 -> {
                    currentValueTitle = 1063; currentValueDescriptions = 1063; shortCode()
                }

                R.id.chapter_1065 -> {
                    currentValueTitle = 1064; currentValueDescriptions = 1064; shortCode()
                }

                R.id.chapter_1066 -> {
                    currentValueTitle = 1065; currentValueDescriptions = 1065; shortCode()
                }

                R.id.chapter_1067 -> {
                    currentValueTitle = 1066; currentValueDescriptions = 1066; shortCode()
                }

                R.id.chapter_1068 -> {
                    currentValueTitle = 1067; currentValueDescriptions = 1067; shortCode()
                }

                R.id.chapter_1069 -> {
                    currentValueTitle = 1068; currentValueDescriptions = 1068; shortCode()
                }

                R.id.chapter_1070 -> {
                    currentValueTitle = 1069; currentValueDescriptions = 1069; shortCode()
                }

                R.id.chapter_1071 -> {
                    currentValueTitle = 1070; currentValueDescriptions = 1070; shortCode()
                }

                R.id.chapter_1072 -> {
                    currentValueTitle = 1071; currentValueDescriptions = 1071; shortCode()
                }

                R.id.chapter_1073 -> {
                    currentValueTitle = 1072; currentValueDescriptions = 1072; shortCode()
                }

                R.id.chapter_1074 -> {
                    currentValueTitle = 1073; currentValueDescriptions = 1073; shortCode()
                }

                R.id.chapter_1075 -> {
                    currentValueTitle = 1074; currentValueDescriptions = 1074; shortCode()
                }

                R.id.chapter_1076 -> {
                    currentValueTitle = 1075; currentValueDescriptions = 1075; shortCode()
                }

                R.id.chapter_1077 -> {
                    currentValueTitle = 1076; currentValueDescriptions = 1076; shortCode()
                }

                R.id.chapter_1078 -> {
                    currentValueTitle = 1077; currentValueDescriptions = 1077; shortCode()
                }

                R.id.chapter_1079 -> {
                    currentValueTitle = 1078; currentValueDescriptions = 1078; shortCode()
                }

                R.id.chapter_1080 -> {
                    currentValueTitle = 1079; currentValueDescriptions = 1079; shortCode()
                }

                R.id.chapter_1081 -> {
                    currentValueTitle = 1080; currentValueDescriptions = 1080; shortCode()
                }

                R.id.chapter_1082 -> {
                    currentValueTitle = 1081; currentValueDescriptions = 1081; shortCode()
                }

                R.id.chapter_1083 -> {
                    currentValueTitle = 1082; currentValueDescriptions = 1082; shortCode()
                }

                R.id.chapter_1084 -> {
                    currentValueTitle = 1083; currentValueDescriptions = 1083; shortCode()
                }

                R.id.chapter_1085 -> {
                    currentValueTitle = 1084; currentValueDescriptions = 1084; shortCode()
                }

                R.id.chapter_1086 -> {
                    currentValueTitle = 1085; currentValueDescriptions = 1085; shortCode()
                }

                R.id.chapter_1087 -> {
                    currentValueTitle = 1086; currentValueDescriptions = 1086; shortCode()
                }

                R.id.chapter_1088 -> {
                    currentValueTitle = 1087; currentValueDescriptions = 1087; shortCode()
                }

                R.id.chapter_1089 -> {
                    currentValueTitle = 1088; currentValueDescriptions = 1088; shortCode()
                }

                R.id.chapter_1090 -> {
                    currentValueTitle = 1089; currentValueDescriptions = 1089; shortCode()
                }

                R.id.chapter_1091 -> {
                    currentValueTitle = 1090; currentValueDescriptions = 1090; shortCode()
                }

                R.id.chapter_1092 -> {
                    currentValueTitle = 1091; currentValueDescriptions = 1091; shortCode()
                }

                R.id.chapter_1093 -> {
                    currentValueTitle = 1092; currentValueDescriptions = 1092; shortCode()
                }

                R.id.chapter_1094 -> {
                    currentValueTitle = 1093; currentValueDescriptions = 1093; shortCode()
                }

                R.id.chapter_1095 -> {
                    currentValueTitle = 1094; currentValueDescriptions = 1094; shortCode()
                }

                R.id.chapter_1096 -> {
                    currentValueTitle = 1095; currentValueDescriptions = 1095; shortCode()
                }

                R.id.chapter_1097 -> {
                    currentValueTitle = 1096; currentValueDescriptions = 1096; shortCode()
                }

                R.id.chapter_1098 -> {
                    currentValueTitle = 1097; currentValueDescriptions = 1097; shortCode()
                }

                R.id.chapter_1099 -> {
                    currentValueTitle = 1098; currentValueDescriptions = 1098; shortCode()
                }

                R.id.chapter_1100 -> {
                    currentValueTitle = 1099; currentValueDescriptions = 1099; shortCode()
                }

                R.id.chapter_1101 -> {
                    currentValueTitle = 1100; currentValueDescriptions = 1100; shortCode()
                }

                R.id.chapter_1102 -> {
                    currentValueTitle = 1101; currentValueDescriptions = 1101; shortCode()
                }

                R.id.chapter_1103 -> {
                    currentValueTitle = 1102; currentValueDescriptions = 1102; shortCode()
                }

                R.id.chapter_1104 -> {
                    currentValueTitle = 1103; currentValueDescriptions = 1103; shortCode()
                }

                R.id.chapter_1105 -> {
                    currentValueTitle = 1104; currentValueDescriptions = 1104; shortCode()
                }

                R.id.chapter_1106 -> {
                    currentValueTitle = 1105; currentValueDescriptions = 1105; shortCode()
                }

                R.id.chapter_1107 -> {
                    currentValueTitle = 1106; currentValueDescriptions = 1106; shortCode()
                }

                R.id.chapter_1108 -> {
                    currentValueTitle = 1107; currentValueDescriptions = 1107; shortCode()
                }

                R.id.chapter_1109 -> {
                    currentValueTitle = 1108; currentValueDescriptions = 1108; shortCode()
                }

                R.id.chapter_1110 -> {
                    currentValueTitle = 1109; currentValueDescriptions = 1109; shortCode()
                }

                R.id.chapter_1111 -> {
                    currentValueTitle = 1110; currentValueDescriptions = 1110; shortCode()
                }

                R.id.chapter_1112 -> {
                    currentValueTitle = 1111; currentValueDescriptions = 1111; shortCode()
                }

                R.id.chapter_1113 -> {
                    currentValueTitle = 1112; currentValueDescriptions = 1112; shortCode()
                }

                R.id.chapter_1114 -> {
                    currentValueTitle = 1113; currentValueDescriptions = 1113; shortCode()
                }

                R.id.chapter_1115 -> {
                    currentValueTitle = 1114; currentValueDescriptions = 1114; shortCode()
                }

                R.id.chapter_1116 -> {
                    currentValueTitle = 1115; currentValueDescriptions = 1115; shortCode()
                }

                R.id.chapter_1117 -> {
                    currentValueTitle = 1116; currentValueDescriptions = 1116; shortCode()
                }

                R.id.chapter_1118 -> {
                    currentValueTitle = 1117; currentValueDescriptions = 1117; shortCode()
                }

                R.id.chapter_1119 -> {
                    currentValueTitle = 1118; currentValueDescriptions = 1118; shortCode()
                }

                R.id.chapter_1120 -> {
                    currentValueTitle = 1119; currentValueDescriptions = 1119; shortCode()
                }

                R.id.chapter_1121 -> {
                    currentValueTitle = 1120; currentValueDescriptions = 1120; shortCode()
                }

                R.id.chapter_1122 -> {
                    currentValueTitle = 1121; currentValueDescriptions = 1121; shortCode()
                }

                R.id.chapter_1123 -> {
                    currentValueTitle = 1122; currentValueDescriptions = 1122; shortCode()
                }

                R.id.chapter_1124 -> {
                    currentValueTitle = 1123; currentValueDescriptions = 1123; shortCode()
                }

                R.id.chapter_1125 -> {
                    currentValueTitle = 1124; currentValueDescriptions = 1124; shortCode()
                }

                R.id.chapter_1126 -> {
                    currentValueTitle = 1125; currentValueDescriptions = 1125; shortCode()
                }

                R.id.chapter_1127 -> {
                    currentValueTitle = 1126; currentValueDescriptions = 1126; shortCode()
                }

                R.id.chapter_1128 -> {
                    currentValueTitle = 1127; currentValueDescriptions = 1127; shortCode()
                }

                R.id.chapter_1129 -> {
                    currentValueTitle = 1128; currentValueDescriptions = 1128; shortCode()
                }

                R.id.chapter_1130 -> {
                    currentValueTitle = 1129; currentValueDescriptions = 1129; shortCode()
                }

                R.id.chapter_1131 -> {
                    currentValueTitle = 1130; currentValueDescriptions = 1130; shortCode()
                }

                R.id.chapter_1132 -> {
                    currentValueTitle = 1131; currentValueDescriptions = 1131; shortCode()
                }

                R.id.chapter_1133 -> {
                    currentValueTitle = 1132; currentValueDescriptions = 1132; shortCode()
                }

                R.id.chapter_1134 -> {
                    currentValueTitle = 1133; currentValueDescriptions = 1133; shortCode()
                }

                R.id.chapter_1135 -> {
                    currentValueTitle = 1134; currentValueDescriptions = 1134; shortCode()
                }

                R.id.chapter_1136 -> {
                    currentValueTitle = 1135; currentValueDescriptions = 1135; shortCode()
                }

                R.id.chapter_1137 -> {
                    currentValueTitle = 1136; currentValueDescriptions = 1136; shortCode()
                }

                R.id.chapter_1138 -> {
                    currentValueTitle = 1137; currentValueDescriptions = 1137; shortCode()
                }

                R.id.chapter_1139 -> {
                    currentValueTitle = 1138; currentValueDescriptions = 1138; shortCode()
                }

                R.id.chapter_1140 -> {
                    currentValueTitle = 1139; currentValueDescriptions = 1139; shortCode()
                }

                R.id.chapter_1141 -> {
                    currentValueTitle = 1140; currentValueDescriptions = 1140; shortCode()
                }

                R.id.chapter_1142 -> {
                    currentValueTitle = 1141; currentValueDescriptions = 1141; shortCode()
                }

                R.id.chapter_1143 -> {
                    currentValueTitle = 1142; currentValueDescriptions = 1142; shortCode()
                }

                R.id.chapter_1144 -> {
                    currentValueTitle = 1143; currentValueDescriptions = 1143; shortCode()
                }

                R.id.chapter_1145 -> {
                    currentValueTitle = 1144; currentValueDescriptions = 1144; shortCode()
                }

                R.id.chapter_1146 -> {
                    currentValueTitle = 1145; currentValueDescriptions = 1145; shortCode()
                }

                R.id.chapter_1147 -> {
                    currentValueTitle = 1146; currentValueDescriptions = 1146; shortCode()
                }

                R.id.chapter_1148 -> {
                    currentValueTitle = 1147; currentValueDescriptions = 1147; shortCode()
                }

                R.id.chapter_1149 -> {
                    currentValueTitle = 1148; currentValueDescriptions = 1148; shortCode()
                }

                R.id.chapter_1150 -> {
                    currentValueTitle = 1149; currentValueDescriptions = 1149; shortCode()
                }

                R.id.chapter_1151 -> {
                    currentValueTitle = 1150; currentValueDescriptions = 1150; shortCode()
                }

                R.id.chapter_1152 -> {
                    currentValueTitle = 1151; currentValueDescriptions = 1151; shortCode()
                }

                R.id.chapter_1153 -> {
                    currentValueTitle = 1152; currentValueDescriptions = 1152; shortCode()
                }

                R.id.chapter_1154 -> {
                    currentValueTitle = 1153; currentValueDescriptions = 1153; shortCode()
                }

                R.id.chapter_1155 -> {
                    currentValueTitle = 1154; currentValueDescriptions = 1154; shortCode()
                }

                R.id.chapter_1156 -> {
                    currentValueTitle = 1155; currentValueDescriptions = 1155; shortCode()
                }

                R.id.chapter_1157 -> {
                    currentValueTitle = 1156; currentValueDescriptions = 1156; shortCode()
                }

                R.id.chapter_1158 -> {
                    currentValueTitle = 1157; currentValueDescriptions = 1157; shortCode()
                }

                R.id.chapter_1159 -> {
                    currentValueTitle = 1158; currentValueDescriptions = 1158; shortCode()
                }

                R.id.chapter_1160 -> {
                    currentValueTitle = 1159; currentValueDescriptions = 1159; shortCode()
                }

                R.id.chapter_1161 -> {
                    currentValueTitle = 1160; currentValueDescriptions = 1160; shortCode()
                }

                R.id.chapter_1162 -> {
                    currentValueTitle = 1161; currentValueDescriptions = 1161; shortCode()
                }

                R.id.chapter_1163 -> {
                    currentValueTitle = 1162; currentValueDescriptions = 1162; shortCode()
                }

                R.id.chapter_1164 -> {
                    currentValueTitle = 1163; currentValueDescriptions = 1163; shortCode()
                }

                R.id.chapter_1165 -> {
                    currentValueTitle = 1164; currentValueDescriptions = 1164; shortCode()
                }

                R.id.chapter_1166 -> {
                    currentValueTitle = 1165; currentValueDescriptions = 1165; shortCode()
                }

                R.id.chapter_1167 -> {
                    currentValueTitle = 1166; currentValueDescriptions = 1166; shortCode()
                }

                R.id.chapter_1168 -> {
                    currentValueTitle = 1167; currentValueDescriptions = 1167; shortCode()
                }

                R.id.chapter_1169 -> {
                    currentValueTitle = 1168; currentValueDescriptions = 1168; shortCode()
                }

                R.id.chapter_1170 -> {
                    currentValueTitle = 1169; currentValueDescriptions = 1169; shortCode()
                }

                R.id.chapter_1171 -> {
                    currentValueTitle = 1170; currentValueDescriptions = 1170; shortCode()
                }

                R.id.chapter_1172 -> {
                    currentValueTitle = 1171; currentValueDescriptions = 1171; shortCode()
                }

                R.id.chapter_1173 -> {
                    currentValueTitle = 1172; currentValueDescriptions = 1172; shortCode()
                }

                R.id.chapter_1174 -> {
                    currentValueTitle = 1173; currentValueDescriptions = 1173; shortCode()
                }

                R.id.chapter_1175 -> {
                    currentValueTitle = 1174; currentValueDescriptions = 1174; shortCode()
                }

                R.id.chapter_1176 -> {
                    currentValueTitle = 1175; currentValueDescriptions = 1175; shortCode()
                }

                R.id.chapter_1177 -> {
                    currentValueTitle = 1176; currentValueDescriptions = 1176; shortCode()
                }

                R.id.chapter_1178 -> {
                    currentValueTitle = 1177; currentValueDescriptions = 1177; shortCode()
                }

                R.id.chapter_1179 -> {
                    currentValueTitle = 1178; currentValueDescriptions = 1178; shortCode()
                }

                R.id.chapter_1180 -> {
                    currentValueTitle = 1179; currentValueDescriptions = 1179; shortCode()
                }

                R.id.chapter_1181 -> {
                    currentValueTitle = 1180; currentValueDescriptions = 1180; shortCode()
                }

                R.id.chapter_1182 -> {
                    currentValueTitle = 1181; currentValueDescriptions = 1181; shortCode()
                }

                R.id.chapter_1183 -> {
                    currentValueTitle = 1182; currentValueDescriptions = 1182; shortCode()
                }

                R.id.chapter_1184 -> {
                    currentValueTitle = 1183; currentValueDescriptions = 1183; shortCode()
                }

                R.id.chapter_1185 -> {
                    currentValueTitle = 1184; currentValueDescriptions = 1184; shortCode()
                }

                R.id.chapter_1186 -> {
                    currentValueTitle = 1185; currentValueDescriptions = 1185; shortCode()
                }

                R.id.chapter_1187 -> {
                    currentValueTitle = 1186; currentValueDescriptions = 1186; shortCode()
                }

                R.id.chapter_1188 -> {
                    currentValueTitle = 1187; currentValueDescriptions = 1187; shortCode()
                }

                R.id.chapter_1189 -> {
                    currentValueTitle = 1188; currentValueDescriptions = 1188; shortCode()
                }

                R.id.chapter_1190 -> {
                    currentValueTitle = 1189; currentValueDescriptions = 1189; shortCode()
                }

                R.id.chapter_1191 -> {
                    currentValueTitle = 1190; currentValueDescriptions = 1190; shortCode()
                }

                R.id.chapter_1192 -> {
                    currentValueTitle = 1191; currentValueDescriptions = 1191; shortCode()
                }

                R.id.chapter_1193 -> {
                    currentValueTitle = 1192; currentValueDescriptions = 1192; shortCode()
                }

                R.id.chapter_1194 -> {
                    currentValueTitle = 1193; currentValueDescriptions = 1193; shortCode()
                }

                R.id.chapter_1195 -> {
                    currentValueTitle = 1194; currentValueDescriptions = 1194; shortCode()
                }

                R.id.chapter_1196 -> {
                    currentValueTitle = 1195; currentValueDescriptions = 1195; shortCode()
                }

                R.id.chapter_1197 -> {
                    currentValueTitle = 1196; currentValueDescriptions = 1196; shortCode()
                }

                R.id.chapter_1198 -> {
                    currentValueTitle = 1197; currentValueDescriptions = 1197; shortCode()
                }

                R.id.chapter_1199 -> {
                    currentValueTitle = 1198; currentValueDescriptions = 1198; shortCode()
                }

                R.id.chapter_1200 -> {
                    currentValueTitle = 1199; currentValueDescriptions = 1199; shortCode()
                }

                R.id.chapter_1201 -> {
                    currentValueTitle = 1200; currentValueDescriptions = 1200; shortCode()
                }

                R.id.chapter_1202 -> {
                    currentValueTitle = 1201; currentValueDescriptions = 1201; shortCode()
                }

                R.id.chapter_1203 -> {
                    currentValueTitle = 1202; currentValueDescriptions = 1202; shortCode()
                }

                R.id.chapter_1204 -> {
                    currentValueTitle = 1203; currentValueDescriptions = 1203; shortCode()
                }

                R.id.chapter_1205 -> {
                    currentValueTitle = 1204; currentValueDescriptions = 1204; shortCode()
                }

                R.id.chapter_1206 -> {
                    currentValueTitle = 1205; currentValueDescriptions = 1205; shortCode()
                }

                R.id.chapter_1207 -> {
                    currentValueTitle = 1206; currentValueDescriptions = 1206; shortCode()
                }

                R.id.chapter_1208 -> {
                    currentValueTitle = 1207; currentValueDescriptions = 1207; shortCode()
                }

                R.id.chapter_1209 -> {
                    currentValueTitle = 1208; currentValueDescriptions = 1208; shortCode()
                }

                R.id.chapter_1210 -> {
                    currentValueTitle = 1209; currentValueDescriptions = 1209; shortCode()
                }

                R.id.chapter_1211 -> {
                    currentValueTitle = 1210; currentValueDescriptions = 1210; shortCode()
                }

                R.id.chapter_1212 -> {
                    currentValueTitle = 1211; currentValueDescriptions = 1211; shortCode()
                }

                R.id.chapter_1213 -> {
                    currentValueTitle = 1212; currentValueDescriptions = 1212; shortCode()
                }

                R.id.chapter_1214 -> {
                    currentValueTitle = 1213; currentValueDescriptions = 1213; shortCode()
                }

                R.id.chapter_1215 -> {
                    currentValueTitle = 1214; currentValueDescriptions = 1214; shortCode()
                }

                R.id.chapter_1216 -> {
                    currentValueTitle = 1215; currentValueDescriptions = 1215; shortCode()
                }

                R.id.chapter_1217 -> {
                    currentValueTitle = 1216; currentValueDescriptions = 1216; shortCode()
                }

                R.id.chapter_1218 -> {
                    currentValueTitle = 1217; currentValueDescriptions = 1217; shortCode()
                }

                R.id.chapter_1219 -> {
                    currentValueTitle = 1218; currentValueDescriptions = 1218; shortCode()
                }

                R.id.chapter_1220 -> {
                    currentValueTitle = 1219; currentValueDescriptions = 1219; shortCode()
                }

                R.id.chapter_1221 -> {
                    currentValueTitle = 1220; currentValueDescriptions = 1220; shortCode()
                }

                R.id.chapter_1222 -> {
                    currentValueTitle = 1221; currentValueDescriptions = 1221; shortCode()
                }

                R.id.chapter_1223 -> {
                    currentValueTitle = 1222; currentValueDescriptions = 1222; shortCode()
                }

                R.id.chapter_1224 -> {
                    currentValueTitle = 1223; currentValueDescriptions = 1223; shortCode()
                }

                R.id.chapter_1225 -> {
                    currentValueTitle = 1224; currentValueDescriptions = 1224; shortCode()
                }

                R.id.chapter_1226 -> {
                    currentValueTitle = 1225; currentValueDescriptions = 1225; shortCode()
                }

                R.id.chapter_1227 -> {
                    currentValueTitle = 1226; currentValueDescriptions = 1226; shortCode()
                }

                R.id.chapter_1228 -> {
                    currentValueTitle = 1227; currentValueDescriptions = 1227; shortCode()
                }

                R.id.chapter_1229 -> {
                    currentValueTitle = 1228; currentValueDescriptions = 1228; shortCode()
                }

                R.id.chapter_1230 -> {
                    currentValueTitle = 1229; currentValueDescriptions = 1229; shortCode()
                }

                R.id.chapter_1231 -> {
                    currentValueTitle = 1230; currentValueDescriptions = 1230; shortCode()
                }

                R.id.chapter_1232 -> {
                    currentValueTitle = 1231; currentValueDescriptions = 1231; shortCode()
                }

                R.id.chapter_1233 -> {
                    currentValueTitle = 1232; currentValueDescriptions = 1232; shortCode()
                }

                R.id.chapter_1234 -> {
                    currentValueTitle = 1233; currentValueDescriptions = 1233; shortCode()
                }

                R.id.chapter_1235 -> {
                    currentValueTitle = 1234; currentValueDescriptions = 1234; shortCode()
                }

                R.id.chapter_1236 -> {
                    currentValueTitle = 1235; currentValueDescriptions = 1235; shortCode()
                }

                R.id.chapter_1237 -> {
                    currentValueTitle = 1236; currentValueDescriptions = 1236; shortCode()
                }

                R.id.chapter_1238 -> {
                    currentValueTitle = 1237; currentValueDescriptions = 1237; shortCode()
                }

                R.id.chapter_1239 -> {
                    currentValueTitle = 1238; currentValueDescriptions = 1238; shortCode()
                }

                R.id.chapter_1240 -> {
                    currentValueTitle = 1239; currentValueDescriptions = 1239; shortCode()
                }

                R.id.chapter_1241 -> {
                    currentValueTitle = 1240; currentValueDescriptions = 1240; shortCode()
                }

                R.id.chapter_1242 -> {
                    currentValueTitle = 1241; currentValueDescriptions = 1241; shortCode()
                }

                R.id.chapter_1243 -> {
                    currentValueTitle = 1242; currentValueDescriptions = 1242; shortCode()
                }

                R.id.chapter_1244 -> {
                    currentValueTitle = 1243; currentValueDescriptions = 1243; shortCode()
                }

                R.id.chapter_1245 -> {
                    currentValueTitle = 1244; currentValueDescriptions = 1244; shortCode()
                }

                R.id.chapter_1246 -> {
                    currentValueTitle = 1245; currentValueDescriptions = 1245; shortCode()
                }

                R.id.chapter_1247 -> {
                    currentValueTitle = 1246; currentValueDescriptions = 1246; shortCode()
                }

                R.id.chapter_1248 -> {
                    currentValueTitle = 1247; currentValueDescriptions = 1247; shortCode()
                }

                R.id.chapter_1249 -> {
                    currentValueTitle = 1248; currentValueDescriptions = 1248; shortCode()
                }

                R.id.chapter_1250 -> {
                    currentValueTitle = 1249; currentValueDescriptions = 1249; shortCode()
                }

                R.id.chapter_1251 -> {
                    currentValueTitle = 1250; currentValueDescriptions = 1250; shortCode()
                }

                R.id.chapter_1252 -> {
                    currentValueTitle = 1251; currentValueDescriptions = 1251; shortCode()
                }

                R.id.chapter_1253 -> {
                    currentValueTitle = 1252; currentValueDescriptions = 1252; shortCode()
                }

                R.id.chapter_1254 -> {
                    currentValueTitle = 1253; currentValueDescriptions = 1253; shortCode()
                }

                R.id.chapter_1255 -> {
                    currentValueTitle = 1254; currentValueDescriptions = 1254; shortCode()
                }

                R.id.chapter_1256 -> {
                    currentValueTitle = 1255; currentValueDescriptions = 1255; shortCode()
                }

                R.id.chapter_1257 -> {
                    currentValueTitle = 1256; currentValueDescriptions = 1256; shortCode()
                }

                R.id.chapter_1258 -> {
                    currentValueTitle = 1257; currentValueDescriptions = 1257; shortCode()
                }

                R.id.chapter_1259 -> {
                    currentValueTitle = 1258; currentValueDescriptions = 1258; shortCode()
                }

                R.id.chapter_1260 -> {
                    currentValueTitle = 1259; currentValueDescriptions = 1259; shortCode()
                }

                R.id.chapter_1261 -> {
                    currentValueTitle = 1260; currentValueDescriptions = 1260; shortCode()
                }

                R.id.chapter_1262 -> {
                    currentValueTitle = 1261; currentValueDescriptions = 1261; shortCode()
                }

                R.id.chapter_1263 -> {
                    currentValueTitle = 1262; currentValueDescriptions = 1262; shortCode()
                }

                R.id.chapter_1264 -> {
                    currentValueTitle = 1263; currentValueDescriptions = 1263; shortCode()
                }

                R.id.chapter_1265 -> {
                    currentValueTitle = 1264; currentValueDescriptions = 1264; shortCode()
                }

                R.id.chapter_1266 -> {
                    currentValueTitle = 1265; currentValueDescriptions = 1265; shortCode()
                }

                R.id.chapter_1267 -> {
                    currentValueTitle = 1266; currentValueDescriptions = 1266; shortCode()
                }

                R.id.chapter_1268 -> {
                    currentValueTitle = 1267; currentValueDescriptions = 1267; shortCode()
                }

                R.id.chapter_1269 -> {
                    currentValueTitle = 1268; currentValueDescriptions = 1268; shortCode()
                }

                R.id.chapter_1270 -> {
                    currentValueTitle = 1269; currentValueDescriptions = 1269; shortCode()
                }

                R.id.chapter_1271 -> {
                    currentValueTitle = 1270; currentValueDescriptions = 1270; shortCode()
                }

                R.id.chapter_1272 -> {
                    currentValueTitle = 1271; currentValueDescriptions = 1271; shortCode()
                }

                R.id.chapter_1273 -> {
                    currentValueTitle = 1272; currentValueDescriptions = 1272; shortCode()
                }

                R.id.chapter_1274 -> {
                    currentValueTitle = 1273; currentValueDescriptions = 1273; shortCode()
                }

                R.id.chapter_1275 -> {
                    currentValueTitle = 1274; currentValueDescriptions = 1274; shortCode()
                }

                R.id.chapter_1276 -> {
                    currentValueTitle = 1275; currentValueDescriptions = 1275; shortCode()
                }

                R.id.chapter_1277 -> {
                    currentValueTitle = 1276; currentValueDescriptions = 1276; shortCode()
                }

                R.id.chapter_1278 -> {
                    currentValueTitle = 1277; currentValueDescriptions = 1277; shortCode()
                }

                R.id.chapter_1279 -> {
                    currentValueTitle = 1278; currentValueDescriptions = 1278; shortCode()
                }

                R.id.chapter_1280 -> {
                    currentValueTitle = 1279; currentValueDescriptions = 1279; shortCode()
                }

                R.id.chapter_1281 -> {
                    currentValueTitle = 1280; currentValueDescriptions = 1280; shortCode()
                }

                R.id.chapter_1282 -> {
                    currentValueTitle = 1281; currentValueDescriptions = 1281; shortCode()
                }

                R.id.chapter_1283 -> {
                    currentValueTitle = 1282; currentValueDescriptions = 1282; shortCode()
                }

                R.id.chapter_1284 -> {
                    currentValueTitle = 1283; currentValueDescriptions = 1283; shortCode()
                }

                R.id.chapter_1285 -> {
                    currentValueTitle = 1284; currentValueDescriptions = 1284; shortCode()
                }

                R.id.chapter_1286 -> {
                    currentValueTitle = 1285; currentValueDescriptions = 1285; shortCode()
                }

                R.id.chapter_1287 -> {
                    currentValueTitle = 1286; currentValueDescriptions = 1286; shortCode()
                }

                R.id.chapter_1288 -> {
                    currentValueTitle = 1287; currentValueDescriptions = 1287; shortCode()
                }

                R.id.chapter_1289 -> {
                    currentValueTitle = 1288; currentValueDescriptions = 1288; shortCode()
                }

                R.id.chapter_1290 -> {
                    currentValueTitle = 1289; currentValueDescriptions = 1289; shortCode()
                }

                R.id.chapter_1291 -> {
                    currentValueTitle = 1290; currentValueDescriptions = 1290; shortCode()
                }

                R.id.chapter_1292 -> {
                    currentValueTitle = 1291; currentValueDescriptions = 1291; shortCode()
                }

                R.id.chapter_1293 -> {
                    currentValueTitle = 1292; currentValueDescriptions = 1292; shortCode()
                }

                R.id.chapter_1294 -> {
                    currentValueTitle = 1293; currentValueDescriptions = 1293; shortCode()
                }

                R.id.chapter_1295 -> {
                    currentValueTitle = 1294; currentValueDescriptions = 1294; shortCode()
                }

                R.id.chapter_1296 -> {
                    currentValueTitle = 1295; currentValueDescriptions = 1295; shortCode()
                }

                R.id.chapter_1297 -> {
                    currentValueTitle = 1296; currentValueDescriptions = 1296; shortCode()
                }

                R.id.chapter_1298 -> {
                    currentValueTitle = 1297; currentValueDescriptions = 1297; shortCode()
                }

                R.id.chapter_1299 -> {
                    currentValueTitle = 1298; currentValueDescriptions = 1298; shortCode()
                }

                R.id.chapter_1300 -> {
                    currentValueTitle = 1299; currentValueDescriptions = 1299; shortCode()
                }

                R.id.chapter_1301 -> {
                    currentValueTitle = 1300; currentValueDescriptions = 1300; shortCode()
                }

                R.id.chapter_1302 -> {
                    currentValueTitle = 1301; currentValueDescriptions = 1301; shortCode()
                }

                R.id.chapter_1303 -> {
                    currentValueTitle = 1302; currentValueDescriptions = 1302; shortCode()
                }

                R.id.chapter_1304 -> {
                    currentValueTitle = 1303; currentValueDescriptions = 1303; shortCode()
                }

                R.id.chapter_1305 -> {
                    currentValueTitle = 1304; currentValueDescriptions = 1304; shortCode()
                }

                R.id.chapter_1306 -> {
                    currentValueTitle = 1305; currentValueDescriptions = 1305; shortCode()
                }

                R.id.chapter_1307 -> {
                    currentValueTitle = 1306; currentValueDescriptions = 1306; shortCode()
                }

                R.id.chapter_1308 -> {
                    currentValueTitle = 1307; currentValueDescriptions = 1307; shortCode()
                }

                R.id.chapter_1309 -> {
                    currentValueTitle = 1308; currentValueDescriptions = 1308; shortCode()
                }

                R.id.chapter_1310 -> {
                    currentValueTitle = 1309; currentValueDescriptions = 1309; shortCode()
                }

                R.id.chapter_1311 -> {
                    currentValueTitle = 1310; currentValueDescriptions = 1310; shortCode()
                }

                R.id.chapter_1312 -> {
                    currentValueTitle = 1311; currentValueDescriptions = 1311; shortCode()
                }

                R.id.chapter_1313 -> {
                    currentValueTitle = 1312; currentValueDescriptions = 1312; shortCode()
                }

                R.id.chapter_1314 -> {
                    currentValueTitle = 1313; currentValueDescriptions = 1313; shortCode()
                }

                R.id.chapter_1315 -> {
                    currentValueTitle = 1314; currentValueDescriptions = 1314; shortCode()
                }

                R.id.chapter_1316 -> {
                    currentValueTitle = 1315; currentValueDescriptions = 1315; shortCode()
                }

                R.id.chapter_1317 -> {
                    currentValueTitle = 1316; currentValueDescriptions = 1316; shortCode()
                }

                R.id.chapter_1318 -> {
                    currentValueTitle = 1317; currentValueDescriptions = 1317; shortCode()
                }

                R.id.chapter_1319 -> {
                    currentValueTitle = 1318; currentValueDescriptions = 1318; shortCode()
                }

                R.id.chapter_1320 -> {
                    currentValueTitle = 1319; currentValueDescriptions = 1319; shortCode()
                }

                R.id.chapter_1321 -> {
                    currentValueTitle = 1320; currentValueDescriptions = 1320; shortCode()
                }

                R.id.chapter_1322 -> {
                    currentValueTitle = 1321; currentValueDescriptions = 1321; shortCode()
                }

                R.id.chapter_1323 -> {
                    currentValueTitle = 1322; currentValueDescriptions = 1322; shortCode()
                }

                R.id.chapter_1324 -> {
                    currentValueTitle = 1323; currentValueDescriptions = 1323; shortCode()
                }

                R.id.chapter_1325 -> {
                    currentValueTitle = 1324; currentValueDescriptions = 1324; shortCode()
                }

                R.id.chapter_1326 -> {
                    currentValueTitle = 1325; currentValueDescriptions = 1325; shortCode()
                }

                R.id.chapter_1327 -> {
                    currentValueTitle = 1326; currentValueDescriptions = 1326; shortCode()
                }

                R.id.chapter_1328 -> {
                    currentValueTitle = 1327; currentValueDescriptions = 1327; shortCode()
                }

                R.id.chapter_1329 -> {
                    currentValueTitle = 1328; currentValueDescriptions = 1328; shortCode()
                }

                R.id.chapter_1330 -> {
                    currentValueTitle = 1329; currentValueDescriptions = 1329; shortCode()
                }

                R.id.chapter_1331 -> {
                    currentValueTitle = 1330; currentValueDescriptions = 1330; shortCode()
                }

                R.id.chapter_1332 -> {
                    currentValueTitle = 1331; currentValueDescriptions = 1331; shortCode()
                }

                R.id.chapter_1333 -> {
                    currentValueTitle = 1332; currentValueDescriptions = 1332; shortCode()
                }

                R.id.chapter_1334 -> {
                    currentValueTitle = 1333; currentValueDescriptions = 1333; shortCode()
                }

                R.id.chapter_1335 -> {
                    currentValueTitle = 1334; currentValueDescriptions = 1334; shortCode()
                }

                R.id.chapter_1336 -> {
                    currentValueTitle = 1335; currentValueDescriptions = 1335; shortCode()
                }

                R.id.chapter_1337 -> {
                    currentValueTitle = 1336; currentValueDescriptions = 1336; shortCode()
                }

                R.id.chapter_1338 -> {
                    currentValueTitle = 1337; currentValueDescriptions = 1337; shortCode()
                }

                R.id.chapter_1339 -> {
                    currentValueTitle = 1338; currentValueDescriptions = 1338; shortCode()
                }

                R.id.chapter_1340 -> {
                    currentValueTitle = 1339; currentValueDescriptions = 1339; shortCode()
                }

                R.id.chapter_1341 -> {
                    currentValueTitle = 1340; currentValueDescriptions = 1340; shortCode()
                }

                R.id.chapter_1342 -> {
                    currentValueTitle = 1341; currentValueDescriptions = 1341; shortCode()
                }

                R.id.chapter_1343 -> {
                    currentValueTitle = 1342; currentValueDescriptions = 1342; shortCode()
                }

                R.id.chapter_1344 -> {
                    currentValueTitle = 1343; currentValueDescriptions = 1343; shortCode()
                }

                R.id.chapter_1345 -> {
                    currentValueTitle = 1344; currentValueDescriptions = 1344; shortCode()
                }

                R.id.chapter_1346 -> {
                    currentValueTitle = 1345; currentValueDescriptions = 1345; shortCode()
                }

                R.id.chapter_1347 -> {
                    currentValueTitle = 1346; currentValueDescriptions = 1346; shortCode()
                }

                R.id.chapter_1348 -> {
                    currentValueTitle = 1347; currentValueDescriptions = 1347; shortCode()
                }

                R.id.chapter_1349 -> {
                    currentValueTitle = 1348; currentValueDescriptions = 1348; shortCode()
                }

                R.id.chapter_1350 -> {
                    currentValueTitle = 1349; currentValueDescriptions = 1349; shortCode()
                }

                R.id.chapter_1351 -> {
                    currentValueTitle = 1350; currentValueDescriptions = 1350; shortCode()
                }

                R.id.chapter_1352 -> {
                    currentValueTitle = 1351; currentValueDescriptions = 1351; shortCode()
                }

                R.id.chapter_1353 -> {
                    currentValueTitle = 1352; currentValueDescriptions = 1352; shortCode()
                }

                R.id.chapter_1354 -> {
                    currentValueTitle = 1353; currentValueDescriptions = 1353; shortCode()
                }

                R.id.chapter_1355 -> {
                    currentValueTitle = 1354; currentValueDescriptions = 1354; shortCode()
                }

                R.id.chapter_1356 -> {
                    currentValueTitle = 1355; currentValueDescriptions = 1355; shortCode()
                }

                R.id.chapter_1357 -> {
                    currentValueTitle = 1356; currentValueDescriptions = 1356; shortCode()
                }

                R.id.chapter_1358 -> {
                    currentValueTitle = 1357; currentValueDescriptions = 1357; shortCode()
                }

                R.id.chapter_1359 -> {
                    currentValueTitle = 1358; currentValueDescriptions = 1358; shortCode()
                }

                R.id.chapter_1360 -> {
                    currentValueTitle = 1359; currentValueDescriptions = 1359; shortCode()
                }

                R.id.chapter_1361 -> {
                    currentValueTitle = 1360; currentValueDescriptions = 1360; shortCode()
                }

                R.id.chapter_1362 -> {
                    currentValueTitle = 1361; currentValueDescriptions = 1361; shortCode()
                }
            }
            true

            //-- Listener Navigation --
        }

    }
}