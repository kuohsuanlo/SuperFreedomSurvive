package SuperFreedomSurvive.Sign.Function;

import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

import java.util.regex.Pattern;

// 傳送
final public class Tp {
    // 傳送

    static final public void Use(Player player, Block block) {

        // 取得字串內容
        Sign sign = (Sign) block.getState();
        // 取得告示牌內容
        String[] text = sign.getLines();


        // 0 [傳送]
        // 1 (座標),(座標),(座標)

        // 檢查格式是否正確
        if (text[1] != null) {
            // 是否有三個數值
            //lore.add( "§r§f 訊息 :" );
            // 正則切割
            Pattern p = Pattern.compile("\\s");
            String[] str = p.split(text[1]);
            // 是否 = 3
            if (str.length == 3) {
                boolean ok = true;
                for (int i = 0, s = str.length; i < s; ++i) {
                    // 檢查是否為整數 或 負數
                    if (
                            str[i].matches("[0-9]{1,8}") ||
                            str[i].matches("[\\-0-9]{1,9}")
                    ) {
                        // 正確
                    } else {
                        // 不正確
                        ok = false;
                    }
                }
                if (ok) {
                    // 允許
                    // 檢查目的是否與告示牌位置 100格內
                    if (
                            Math.abs(block.getX() - Integer.parseInt(str[0])) <= 100 &&
                                    Math.abs(block.getY() - Integer.parseInt(str[1])) <= 100 &&
                                    Math.abs(block.getZ() - Integer.parseInt(str[2])) <= 100
                    ) {
                        // 是
                        int X = Integer.parseInt(str[0]);
                        int Y = Integer.parseInt(str[1]);
                        int Z = Integer.parseInt(str[2]);

                        if (X < 0) {
                            X = X - 1;
                        }

                        if (Z < 0) {
                            Z = Z - 1;
                        }

                        // 進行傳送
                        SuperFreedomSurvive.Player.Teleport.Location(
                                player,
                                player.getLocation().getWorld().getName(),
                                X,
                                Y,
                                Z
                        );

                        player.sendMessage("[告示牌系統]  傳送成功");

                    } else {
                        // 差太遠
                        player.sendMessage("[告示牌系統]  §c目的座標只能在距離告示牌 半徑 100 格內");
                    }
                } else {
                    // 不允許
                    player.sendMessage("[告示牌系統]  §c不正確的座標值 X Y Z 只能是整數數字");
                }
            } else {
                // 告知
                player.sendMessage("[告示牌系統]  §c不正確的座標數量 第二行因該是 X Y Z");
            }


        }
    }
}
