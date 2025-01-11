package com.tech.slideshow.themes;


import com.tech.slideshow.R;

import java.util.ArrayList;
import com.tech.slideshow.mask.FinalMaskBitmap.EFFECT;

public enum THEMES {
    Shine("Shine") {
        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> mEffects = new ArrayList<>();
            mEffects.add(EFFECT.PIN_WHEEL);
            mEffects.add(EFFECT.SKEW_RIGHT_SPLIT);
            mEffects.add(EFFECT.SKEW_LEFT_SPLIT);
            mEffects.add(EFFECT.SKEW_RIGHT_MEARGE);
            mEffects.add(EFFECT.SKEW_LEFT_MEARGE);
            mEffects.add(EFFECT.FOUR_TRIANGLE);
            mEffects.add(EFFECT.SQUARE_IN);
            mEffects.add(EFFECT.SQUARE_OUT);
            mEffects.add(EFFECT.CIRCLE_LEFT_BOTTOM);
            mEffects.add(EFFECT.CIRCLE_IN);
            mEffects.add(EFFECT.DIAMOND_OUT);
            mEffects.add(EFFECT.HORIZONTAL_COLUMN_DOWNMASK);
            mEffects.add(EFFECT.RECT_RANDOM);
            mEffects.add(EFFECT.CROSS_IN);
            mEffects.add(EFFECT.DIAMOND_IN);
            return mEffects;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return null;
        }

        public int getThemeDrawable() {
            return R.drawable.theme_shine;
        }

        public int getThemeMusic() {
            return R.raw.music_1;
        }
    },
    Shine3D("Shine3D") {
        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> arrayList = new ArrayList<>();
            arrayList.add(EFFECT.Whole3D_BT);
            arrayList.add(EFFECT.Whole3D_TB);
            arrayList.add(EFFECT.Whole3D_LR);
            arrayList.add(EFFECT.Whole3D_RL);
            arrayList.add(EFFECT.SepartConbine_BT);
            arrayList.add(EFFECT.SepartConbine_TB);
            arrayList.add(EFFECT.SepartConbine_LR);
            arrayList.add(EFFECT.SepartConbine_RL);
            arrayList.add(EFFECT.RollInTurn_BT);
            arrayList.add(EFFECT.RollInTurn_TB);
            arrayList.add(EFFECT.RollInTurn_LR);
            arrayList.add(EFFECT.RollInTurn_RL);
            arrayList.add(EFFECT.Jalousie_BT);
            arrayList.add(EFFECT.Jalousie_LR);
            arrayList.add(EFFECT.Roll2D_BT);
            arrayList.add(EFFECT.Roll2D_TB);
            arrayList.add(EFFECT.Roll2D_LR);
            arrayList.add(EFFECT.Roll2D_RL);
            return arrayList;

        }

        @Override
        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return null;
        }

        @Override
        public int getThemeDrawable() {
            return R.drawable.theme_shine3d;
        }

        @Override
        public int getThemeMusic() {
            return R.raw.music_1;
        }
    },
    Shuffle_1("Shuffle 1") {
        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> arrayList = new ArrayList<>();
            arrayList.add(EFFECT.ECLIPSE_IN);
            arrayList.add(EFFECT.CIRCLE_LEFT_BOTTOM);
            arrayList.add(EFFECT.CIRCLE_RIGHT_TOP);
            arrayList.add(EFFECT.CIRCLE_LEFT_TOP);
            arrayList.add(EFFECT.CIRCLE_RIGHT_BOTTOM);
            arrayList.add(EFFECT.CIRCLE_IN);
            arrayList.add(EFFECT.CIRCLE_IN);
            arrayList.add(EFFECT.RECT_RANDOM);
            return arrayList;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return null;
        }

        public int getThemeDrawable() {
            return R.drawable.theme_shuffle_1;
        }

        public int getThemeMusic() {
            return R.raw.music_1;
        }
    },
    Shuffle_2("Shuffle 2") {
        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> arrayList = new ArrayList<>();
            arrayList.add(EFFECT.HORIZONTAL_COLUMN_DOWNMASK);
            arrayList.add(EFFECT.Roll2D_TB);
            arrayList.add(EFFECT.Roll2D_LR);
            arrayList.add(EFFECT.Roll2D_BT);
            arrayList.add(EFFECT.Roll2D_RL);
            arrayList.add(EFFECT.RECT_RANDOM);
            arrayList.add(EFFECT.FOUR_TRIANGLE);
            arrayList.add(EFFECT.SKEW_RIGHT_MEARGE);
            return arrayList;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return null;
        }

        public int getThemeDrawable() {
            return R.drawable.theme_shuffle_2;
        }

        public int getThemeMusic() {
            return R.raw.music_1;
        }
    },
    Shuffle_3("Shuffle 3") {
        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> arrayList = new ArrayList<>();
            arrayList.add(EFFECT.SQUARE_IN);
            arrayList.add(EFFECT.SQUARE_OUT);
            arrayList.add(EFFECT.SKEW_RIGHT_SPLIT);
            arrayList.add(EFFECT.SKEW_RIGHT_MEARGE);
            arrayList.add(EFFECT.WIND_MILL);
            arrayList.add(EFFECT.HORIZONTAL_RECT);
            arrayList.add(EFFECT.VERTICAL_RECT);
            arrayList.add(EFFECT.PIN_WHEEL);
            return arrayList;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return null;
        }

        public int getThemeDrawable() {
            return R.drawable.theme_shuffle_3;
        }

        public int getThemeMusic() {
            return R.raw.music_1;
        }
    },

    CIRCLE_IN("Circle In") {
        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> list = new ArrayList<>();
            list.add(EFFECT.CIRCLE_IN);
            return list;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return new ArrayList<>();
        }

        public int getThemeDrawable() {
            return R.drawable.theme_circle_in;
        }

        public int getThemeMusic() {
            return R.raw.music_1;
        }
    },
    CIRCLE_OUT("Circle Out") {
        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> list = new ArrayList<>();
            list.add(EFFECT.CIRCLE_OUT);
            return list;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return new ArrayList<>();
        }

        public int getThemeDrawable() {
            return R.drawable.theme_circle_out;
        }

        public int getThemeMusic() {
            return R.raw.music_1;
        }
    },
    CIRCLE_LEFT_BOTTOM("Circle Left Bottom") {
        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> list = new ArrayList<>();
            list.add(EFFECT.CIRCLE_LEFT_BOTTOM);
            return list;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return new ArrayList<>();
        }

        public int getThemeDrawable() {
            return R.drawable.theme_circle_lb;
        }

        public int getThemeMusic() {
            return R.raw.music_1;
        }
    },
    CIRCLE_LEFT_TOP("Circle Left Top") {
        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> arrayList = new ArrayList<>();
            arrayList.add(EFFECT.CIRCLE_LEFT_TOP);
            return arrayList;
        }

        public int getThemeDrawable() {
            return R.drawable.theme_circle_lt;
        }

        public int getThemeMusic() {
            return R.raw.music_1;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return new ArrayList<>();
        }
    },
    CIRCLE_RIGHT_TOP("Circle Right top") {
        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> arrayList = new ArrayList<>();
            arrayList.add(EFFECT.CIRCLE_RIGHT_TOP);
            return arrayList;
        }

        public int getThemeDrawable() {
            return R.drawable.theme_circle_rt;
        }

        public int getThemeMusic() {
            return R.raw.music_1;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return new ArrayList<>();
        }
    },
    CIRCLE_RIGHT_BOTTOM("Circle Right Bottom") {
        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> list = new ArrayList<>();
            list.add(EFFECT.CIRCLE_RIGHT_BOTTOM);
            return list;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return new ArrayList<>();
        }

        public int getThemeDrawable() {
            return R.drawable.theme_circle_rb;
        }

        public int getThemeMusic() {
            return R.raw.music_1;
        }
    },
    CROSS_IN("Cross In") {
        /* renamed from: a */
        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> arrayList = new ArrayList<>();
            arrayList.add(EFFECT.CROSS_IN);
            return arrayList;
        }

        public int getThemeDrawable() {
            return R.drawable.theme_cross_in;
        }

        public int getThemeMusic() {
            return R.raw.music_1;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return new ArrayList<>();
        }
    },
    CROSS_OUT("Cross Out") {
        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> arrayList = new ArrayList<>();
            arrayList.add(EFFECT.CROSS_OUT);
            return arrayList;
        }

        public int getThemeDrawable() {
            return R.drawable.theme_cross_out;
        }

        public int getThemeMusic() {
            return R.raw.music_1;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return new ArrayList<>();
        }
    },
    DIAMOND_IN("Diamond In") {
        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> list = new ArrayList<>();
            list.add(EFFECT.DIAMOND_IN);
            return list;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return new ArrayList<>();
        }

        public int getThemeDrawable() {
            return R.drawable.theme_diamond_in;
        }

        public int getThemeMusic() {
            return R.raw.music_1;
        }
    },
    DIAMOND_OUT("Diamond out") {
        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> list = new ArrayList<>();
            list.add(EFFECT.DIAMOND_OUT);
            return list;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return new ArrayList<>();
        }

        public int getThemeDrawable() {
            return R.drawable.theme_diamond_out;
        }

        public int getThemeMusic() {
            return R.raw.music_1;
        }
    },
    ECLIPSE_IN("Eclipse In") {
        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> list = new ArrayList<>();
            list.add(EFFECT.ECLIPSE_IN);
            return list;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return new ArrayList<>();
        }

        public int getThemeDrawable() {
            return R.drawable.theme_eclipse_in;
        }

        public int getThemeMusic() {
            return R.raw.music_1;
        }
    },
    FOUR_TRIANGLE("Four Triangle") {
        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> list = new ArrayList<>();
            list.add(EFFECT.FOUR_TRIANGLE);
            return list;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return new ArrayList<>();
        }

        public int getThemeDrawable() {
            return R.drawable.theme_four_triangle;
        }

        public int getThemeMusic() {
            return R.raw.music_1;
        }
    },
    HORIZONTAL_COLUMN_DOWNMASK("Horizontal Column") {
        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> arrayList = new ArrayList<>();
            arrayList.add(EFFECT.HORIZONTAL_COLUMN_DOWNMASK);
            return arrayList;
        }

        public int getThemeDrawable() {
            return R.drawable.theme_horizontal_column;
        }

        public int getThemeMusic() {
            return R.raw.music_1;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return new ArrayList<>();
        }
    },
    LEAF("Leaf") {
        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> arrayList = new ArrayList<>();
            arrayList.add(EFFECT.LEAF);
            return arrayList;
        }

        public int getThemeDrawable() {
            return R.drawable.theme_leaf;
        }

        public int getThemeMusic() {
            return R.raw.music_1;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return new ArrayList<>();
        }
    },
    OPEN_DOOR("Open Door") {
        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> list = new ArrayList<>();
            list.add(EFFECT.OPEN_DOOR);
            return list;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return new ArrayList<>();
        }

        public int getThemeDrawable() {
            return R.drawable.theme_open_door;
        }

        public int getThemeMusic() {
            return R.raw.music_1;
        }
    },
    PIN_WHEEL("Pin Wheel") {
        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> list = new ArrayList<>();
            list.add(EFFECT.PIN_WHEEL);
            return list;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return new ArrayList<>();
        }

        public int getThemeDrawable() {
            return R.drawable.theme_pin_wheel;
        }

        public int getThemeMusic() {
            return R.raw.music_1;
        }
    },
    RECT_RANDOM("Rect Random") {
        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> list = new ArrayList<>();
            list.add(EFFECT.RECT_RANDOM);
            return list;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return new ArrayList<>();
        }

        public int getThemeDrawable() {
            return R.drawable.theme_rect_random;
        }

        public int getThemeMusic() {
            return R.raw.music_1;
        }
    },
    SKEW_LEFT_MEARGE("Skew Left Mearge") {
        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> list = new ArrayList<>();
            list.add(EFFECT.SKEW_LEFT_MEARGE);
            return list;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return new ArrayList<>();
        }

        public int getThemeDrawable() {
            return R.drawable.theme_skew_left_mearge;
        }

        public int getThemeMusic() {
            return R.raw.music_1;
        }
    },
    SKEW_RIGHT_MEARGE("Skew Right Mearge") {
        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> list = new ArrayList<>();
            list.add(EFFECT.SKEW_RIGHT_MEARGE);
            return list;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return new ArrayList<>();
        }

        public int getThemeDrawable() {
            return R.drawable.theme_skew_right_mearge;
        }

        public int getThemeMusic() {
            return R.raw.music_1;
        }
    },
    SKEW_RIGHT_SPLIT("SKEW_RIGHT_SPLIT") {
        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> arrayList = new ArrayList<>();
            arrayList.add(EFFECT.SKEW_RIGHT_SPLIT);
            return arrayList;
        }

        public int getThemeDrawable() {
            return R.drawable.theme_skew_right_split;
        }

        public int getThemeMusic() {
            return R.raw.music_1;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return new ArrayList<>();
        }
    },
    SKEW_LEFT_SPLIT("SKEW_LEFT_SPLIT") {
        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> arrayList = new ArrayList<>();
            arrayList.add(EFFECT.SKEW_LEFT_SPLIT);
            return arrayList;
        }

        public int getThemeDrawable() {
            return R.drawable.theme_skew_left_split;
        }

        public int getThemeMusic() {
            return R.raw.music_1;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return new ArrayList<>();
        }
    },
    SQUARE_OUT("Square Out") {
        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> list = new ArrayList<>();
            list.add(EFFECT.SQUARE_OUT);
            return list;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return new ArrayList<>();
        }

        public int getThemeDrawable() {
            return R.drawable.theme_square_out;
        }

        public int getThemeMusic() {
            return R.raw.music_1;
        }
    },
    SQUARE_IN("Square In") {
        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> list = new ArrayList<>();
            list.add(EFFECT.SQUARE_IN);
            return list;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return new ArrayList<>();
        }

        public int getThemeDrawable() {
            return R.drawable.theme_square_in;
        }

        public int getThemeMusic() {
            return R.raw.music_1;
        }
    },
    HORIZONTAL_RECT("Horizontal Rect") {
        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> arrayList = new ArrayList<>();
            arrayList.add(EFFECT.HORIZONTAL_RECT);
            return arrayList;
        }

        public int getThemeDrawable() {
            return R.drawable.theme_horizotal_rect;
        }

        public int getThemeMusic() {
            return R.raw.music_1;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return new ArrayList<>();
        }
    },
    VERTICAL_RECT("Vertical Rect") {
        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> list = new ArrayList<>();
            list.add(EFFECT.VERTICAL_RECT);
            return list;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return new ArrayList<>();
        }

        public int getThemeDrawable() {
            return R.drawable.theme_vertical_rect;
        }

        public int getThemeMusic() {
            return R.raw.music_1;
        }
    },
    WIND_MILL("Wind Mill") {
        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> arrayList = new ArrayList<>();
            arrayList.add(EFFECT.WIND_MILL);
            return arrayList;
        }

        public int getThemeDrawable() {
            return R.drawable.theme_will_mill;
        }

        public int getThemeMusic() {
            return R.raw.music_1;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return new ArrayList<>();
        }
    },
    Jalousie_BT("Jalousie_BT") {
        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return null;
        }

        public int getThemeDrawable() {
            return R.drawable.theme_jalousie_bt;
        }

        public int getThemeMusic() {
            return R.raw.music_1;
        }

        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> arrayList = new ArrayList<>();
            arrayList.add(EFFECT.Jalousie_BT);
            return arrayList;
        }
    },
    Jalousie_LR("Jalousie_LR") {
        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return null;
        }

        public int getThemeDrawable() {
            return R.drawable.theme_jalousie_lr;
        }

        public int getThemeMusic() {
            return R.raw.music_1;
        }

        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> arrayList = new ArrayList<>();
            arrayList.add(EFFECT.Jalousie_LR);
            return arrayList;
        }
    },
    Whole3D_BT("Whole3D_BT") {
        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return null;
        }

        public int getThemeDrawable() {
            return R.drawable.theme_whole_3d_bt;
        }

        public int getThemeMusic() {
            return R.raw.music_1;
        }

        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> arrayList = new ArrayList<>();
            arrayList.add(EFFECT.Whole3D_BT);
            return arrayList;
        }
    },
    Whole3D_TB("Whole3D_TB") {
        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return null;
        }

        public int getThemeDrawable() {
            return R.drawable.theme_whole_3d_tb;
        }

        public int getThemeMusic() {
            return R.raw.music_1;
        }

        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> arrayList = new ArrayList<>();
            arrayList.add(EFFECT.Whole3D_TB);
            return arrayList;
        }
    },
    Whole3D_LR("Whole3D_LR") {
        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return null;
        }

        public int getThemeDrawable() {
            return R.drawable.theme_whole_3d_lr;
        }

        public int getThemeMusic() {
            return R.raw.music_1;
        }

        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> arrayList = new ArrayList<>();
            arrayList.add(EFFECT.Whole3D_LR);
            return arrayList;
        }
    },
    Whole3D_RL("Whole3D_Rl") {
        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return null;
        }

        public int getThemeDrawable() {
            return R.drawable.theme_whole_3d_rl;
        }

        public int getThemeMusic() {
            return R.raw.music_1;
        }

        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> arrayList = new ArrayList<>();
            arrayList.add(EFFECT.Whole3D_RL);
            return arrayList;
        }
    },
    SepartConbine_BT("SepartConbine_BT") {
        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return null;
        }

        public int getThemeDrawable() {
            return R.drawable.theme_separtconbine_bt;
        }

        public int getThemeMusic() {
            return R.raw.music_1;
        }

        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> arrayList = new ArrayList<>();
            arrayList.add(EFFECT.SepartConbine_BT);
            return arrayList;
        }
    },
    SepartConbine_TB("SepartConbine_TB") {
        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return null;
        }

        public int getThemeDrawable() {
            return R.drawable.theme_separtconbine_tb;
        }

        public int getThemeMusic() {
            return R.raw.music_1;
        }

        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> arrayList = new ArrayList<>();
            arrayList.add(EFFECT.SepartConbine_TB);
            return arrayList;
        }
    },
    SepartConbine_LR("SepartConbine_LR") {
        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return null;
        }

        public int getThemeDrawable() {
            return R.drawable.theme_separtconbine_lr;
        }

        public int getThemeMusic() {
            return R.raw.music_1;
        }

        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> arrayList = new ArrayList<>();
            arrayList.add(EFFECT.SepartConbine_LR);
            return arrayList;
        }
    },
    SepartConbine_RL("SepartConbine_Rl") {
        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return null;
        }

        public int getThemeDrawable() {
            return R.drawable.theme_separtconbine_rl;
        }

        public int getThemeMusic() {
            return R.raw.music_1;
        }

        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> arrayList = new ArrayList<>();
            arrayList.add(EFFECT.SepartConbine_RL);
            return arrayList;
        }
    },
    RollInTurn_RL("RollInTurn_RL") {
        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return null;
        }

        public int getThemeDrawable() {
            return R.drawable.theme_roll_in_turn_rl;
        }

        public int getThemeMusic() {
            return R.raw.music_1;
        }

        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> arrayList = new ArrayList<>();
            arrayList.add(EFFECT.RollInTurn_BT);
            return arrayList;
        }
    },
    RollInTurn_LR("RollInTurn_LR") {
        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return null;
        }

        public int getThemeDrawable() {
            return R.drawable.theme_roll_in_turn_lr;
        }

        public int getThemeMusic() {
            return R.raw.music_1;
        }

        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> arrayList = new ArrayList<>();
            arrayList.add(EFFECT.RollInTurn_TB);
            return arrayList;
        }
    },
    RollInTurn_TB("RollInTurn_TB") {
        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return null;
        }

        public int getThemeDrawable() {
            return R.drawable.theme_roll_in_turn_tb;
        }

        public int getThemeMusic() {
            return R.raw.music_1;
        }

        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> arrayList = new ArrayList<>();
            arrayList.add(EFFECT.RollInTurn_LR);
            return arrayList;
        }
    },
    RollInTurn_BT("RollInTurn_BT") {
        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return null;
        }

        public int getThemeDrawable() {
            return R.drawable.theme_roll_in_turn_bt;
        }

        public int getThemeMusic() {
            return R.raw.music_1;
        }

        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> arrayList = new ArrayList<>();
            arrayList.add(EFFECT.RollInTurn_RL);
            return arrayList;
        }
    },
    Roll2D_BT("Roll2D_BT") {
        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return null;
        }

        public int getThemeDrawable() {
            return R.drawable.theme_roll_2d_bt;
        }

        public int getThemeMusic() {
            return R.raw.music_1;
        }

        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> arrayList = new ArrayList<>();
            arrayList.add(EFFECT.Roll2D_BT);
            return arrayList;
        }
    },
    Roll2D_TB("Roll2D_TB") {
        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return null;
        }

        public int getThemeDrawable() {
            return R.drawable.theme_roll_2d_tb;
        }

        public int getThemeMusic() {
            return R.raw.music_1;
        }

        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> arrayList = new ArrayList<>();
            arrayList.add(EFFECT.Roll2D_TB);
            return arrayList;
        }
    },
    Roll2D_LR("Roll2D_LR") {
        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return null;
        }

        public int getThemeDrawable() {
            return R.drawable.theme_roll_2d_lr;
        }

        public int getThemeMusic() {
            return R.raw.music_1;
        }

        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> arrayList = new ArrayList<>();
            arrayList.add(EFFECT.Roll2D_LR);
            return arrayList;
        }
    },
    Roll2D_Rl("Roll2D_Rl") {
        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return null;
        }

        public int getThemeDrawable() {
            return R.drawable.theme_roll_2d_rl;
        }

        public int getThemeMusic() {
            return R.raw.music_1;
        }

        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> arrayList = new ArrayList<>();
            arrayList.add(EFFECT.Roll2D_RL);
            return arrayList;
        }
    };

    String name;

    public abstract ArrayList<EFFECT> getTheme();

    public abstract ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList);

    public abstract int getThemeDrawable();

    public abstract int getThemeMusic();

    THEMES(String string) {
        this.name = "";
        this.name = string;
    }

    public String getName() {
        return this.name;
    }
}
