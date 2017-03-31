package com.nodlee.theogony.thirdparty.gson;

import android.util.Log;

import com.nodlee.theogony.bean.Var;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * 作者：nodlee
 * 时间：2017/3/30
 * 说明：
 */
public class SpellDeserializerTest {

    @Test
    public void deserialize() throws Exception {
        String title1 = "賈克斯跳向目標，如目標是敵人，則造成 {{ e1 }} （+{{ f1 }}） （+{{ a1 }}）物理傷害。";
        String title2 = "賈克斯強化他的武器，下次基礎攻擊或跳斬附加額外 {{ e1 }} （+{{ a1 }}） 點魔法傷害。";
        String title3 = "賈克斯進入忘我的防禦姿態 {{ e6 }} 秒，閃避所有普通攻擊並且減少範圍技能傷害 {{ e3 }}%。 再次使用技能或是 2 秒後，賈克斯暈眩身邊所有敵人，持續 {{ e2 }} 秒並對他們造成 {{ e1 }} （+{{ f2 }}）物理傷害。 賈克斯每閃過一次攻擊，反擊風暴造成傷害上升{{ e5 }}%（最多提昇 {{ e4 }}% 傷害）。";
        String title4 = "被動：當進行 3 次普攻時，賈克斯可以造成額外 {{ e1 }}﹝+{{ a1 }}﹞魔法傷害。 主動：強化戰鬥的意志，提高 {{ f2 }} 物理防禦和 {{ f1 }} 魔法防禦，持續 {{ e5 }} 秒。 額外的物理防禦等同 {{ e3 }} 加上額外 {{ e6 }}% 物理攻擊。 額外的魔法防禦等同 {{ e3 }} 加上額外 {{ e7 }}% 魔法攻擊。";
        String title5 = "主動： 索娜獲得 {{ f1*100 }}% 跑速【{{ e1 }}% + 每 100 魔法攻擊獲得 {{ f2*100 }}%】，持續 {{ e9 }} 秒（或被敵方攻擊則停止 ），並將力量和弦 附加上 節奏 效果。 旋律： 索娜獲得靈氣，持續 {{ e3 }} 秒。接觸到靈氣的友軍將獲得 {{ f3*100 }}% 跑速，持續 {{ e5 }} 秒。 索娜自身增加的額外跑速將一直持續至少 {{ e5 }} 秒。";

        Var var = new Var("f1", "bonusattackdamage", new float[] { 1.0f });
        Var var2 = new Var("a1", "spelldamage", new float[] { 0.6f });
        List<Var> vars = Arrays.asList(var, var2);

        String[] effectBurns = { "", "70/110/150/190/230", "0", "0", "0", "0", "0", "0", "0", "0", "0" };

        assertTrue(testFormatToolTip(title1, effectBurns, vars));
        assertTrue(testFormatToolTip(title2, effectBurns, vars));
        assertTrue(testFormatToolTip(title3, effectBurns, vars));
        assertTrue(testFormatToolTip(title4, effectBurns, vars));
        assertTrue(testFormatToolTip(title5, effectBurns, vars));
    }

    private boolean testFormatToolTip(String toolTip, String[] effectBurns, List<Var> vars) throws Exception {
        SpellDeserializer deserializer = new SpellDeserializer();
        String formattedToolTip = deserializer.formatToolTip(toolTip, effectBurns, vars);

        Log.d("xxx", "toolTip:" + toolTip);
        Log.d("xxx", "formattedToolTip:" + formattedToolTip);

        return !formattedToolTip.equals(toolTip);
    }
}