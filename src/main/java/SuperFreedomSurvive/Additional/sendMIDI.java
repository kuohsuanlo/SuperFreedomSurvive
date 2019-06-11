package SuperFreedomSurvive.Additional;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Track;
import java.io.File;
import java.util.*;

public class sendMIDI {
    // midi 播放成為音符盒聲音給玩家

    private Plugin plugin = SuperFreedomSurvive.SYSTEM.Plugin.get(); // 取得插件
    private File file = null; // 文件
    private List<Player> players = null; // 玩家清單
    private long tempo = 100; // 節拍速度初始化
    private long tick = 0; // 當前播放的 tick
    private float volumes = 1F; // 初始化 音量
    private BukkitTask run = null; // 線程
    private boolean concentrated = false; // 音階是否要集中
    private boolean end_cache = false; // 播放結束是否將 file = null
    private boolean replay = false; // 播放結束是否自動重播 (end_cache 必須為 false)



    /**
     * @param file_data ./midi/~ 目錄中的MIDI文件名稱
     * @param send_players 要傳遞音樂的玩家清單
     * @param have_concentrated 音階是否要集中
     * @param volume 總體音量
     **/
    // 接口
    public sendMIDI(File file_data , List<Player> send_players , boolean have_concentrated , float volume ) {
        try {
            // 安全處裡
            if(file_data == null)
                throw new NullPointerException();
            if(send_players == null)
                throw new NullPointerException();

            // new File("./midi/" + file_name + ".mid");
            // 導入 midi 資訊
            file = file_data;
            players = send_players;
            concentrated = have_concentrated;
            volumes = volume;

        } catch (Exception ex) {
            // 出錯
            ex.printStackTrace();
            file = null;

        }
    }






    // 外部取得內部資料
    public synchronized File getFile() {
        return file;
    }
    public synchronized List<Player> getPlayers() {
        return players;
    }
    public synchronized BukkitTask getRun() {
        return run;
    }
    public synchronized long getTick() {
        return tick;
    }
    public synchronized long getTempo() {
        return tempo;
    }
    public synchronized double getVolume() {
        return volumes;
    }
    public synchronized boolean isEndCache() {
        return end_cache;
    }
    public synchronized boolean isReplay() {
        return replay;
    }



    // 外部設定內部資料
    public synchronized void setVolume(float volume) {
        volumes = volume;
    }
    public synchronized void setEndCache(boolean volume) {
        end_cache = volume;
    }
    public synchronized void setReplay(boolean volume) {
        replay = volume;
    }



    // 新增 / 移除玩家
    public synchronized void addPlayer(Player player) {
        if (!players.contains(player)) {
            players.add(player);
        }
    }
    public synchronized void removePlayer(Player player) {
        players.remove(player);
    }




    // 是否正常
    public synchronized boolean isOk() {
        if ( file != null ) {
            return true;
        } else {
            return false;
        }
    }

    // 是否開始
    public synchronized boolean isStart() {
        if (run != null) {
            return true;
        } else {
            return false;
        }
    }

    // 音階是否集中
    public synchronized boolean isConcentrated() {
        return concentrated;
    }









    // 開始撥放
    public synchronized boolean start() {
        try {

            if ( run != null ) // 已經在撥放了
                return false;


            if ( isOk() ) { // 確認文件存在

                Sequence sequence = MidiSystem.getSequence(file); // 轉換為音軌資訊

                Track[] tracks = sequence.getTracks(); // 取得所有軌道

                long tick_length = sequence.getTickLength(); // 取得軌道長度

                /*
                流程

                取得頻道
                計算每個頻道音階各階段的數量
                在寫入map的時候進行音頻演算 {樂器編號,音符編號}
                轉交迴圈
                撥放給玩家
                */

                Map<Long, List<Integer[]>> map = new LinkedHashMap<>(); // 初始化緩存數據的 map

                int[] sounds = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}; // 初始化 音色
                int[] pitchs = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}; // 初始化 音高



                // ------------------------------------------- 檢查數組音調 -------------------------------------------
                if ( concentrated ) { // 是否要集中音階

                    // 初始化 通道緩存
                    ArrayList<ArrayList<Integer>> channel = new ArrayList<ArrayList<Integer>>();
                    for (int i = 0; i < 16; i++)
                        channel.add(new ArrayList<Integer>());


                    for (int t = 0; t < tracks.length; ++t) {
                        for (int i = 0, s = tracks[t].size(); i < s; ++i) {
                            byte[] bytes = tracks[t].get(i).getMessage().getMessage();
                            int[] ints = new int[]{bytes[0] & 0xFF, bytes[1] & 0xFF};
                            if (ints[0] >= 144 && ints[0] <= 159) {
                                // 控制代碼 聲音開始
                                ArrayList<Integer> integers = channel.get(decryptStatus(tracks[t].get(i).getMessage().getStatus())[1]); // 新建緩存數組
                                integers.add(ints[1]); // 加入數組
                                channel.set(decryptStatus(tracks[t].get(i).getMessage().getStatus())[1], integers); // 寫入 map
                            }
                        }
                    }


                    // 確保盡量保持在最多可使用的音色
                    // 計算每一個區段的音符數量
                    for (int c = 0; c < channel.size(); ++c) {
                        ArrayList<Integer> integers = channel.get(c);

                        int[] total = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}; // 初始化 各音高區段總數

                        for (int value : integers) {
                            if (value >= 0 && value <= 5) {
                                total[0]++;
                            } else if (value >= 6 && value <= 17) {
                                total[1]++;
                            } else if (value >= 18 && value <= 29) {
                                total[2]++;
                            } else if (value >= 30 && value <= 41) {
                                total[3]++;
                            } else if (value >= 42 && value <= 53) {
                                total[4]++;
                            } else if (value >= 54 && value <= 65) { // 主要區段
                                total[5]++;
                            } else if (value >= 66 && value <= 77) { // 主要區段
                                total[6]++;
                            } else if (value >= 78 && value <= 89) {
                                total[7]++;
                            } else if (value >= 90 && value <= 101) {
                                total[8]++;
                            } else if (value >= 102 && value <= 113) {
                                total[9]++;
                            } else if (value >= 114 && value <= 125) {
                                total[10]++;
                            } else if (value >= 126 && value <= 127) {
                                total[11]++;
                            }
                        }

                        // 檢查總數
                        int all_total = 0;
                        for (int i = 0; i < 11; i++)
                            all_total = all_total + total[i];

                        // 檢查區段最高者
                        int now_tallest_total = 0;

                        for (int i = 0; i < 12; i++)
                            if (total[i] > now_tallest_total) {
                                now_tallest_total = total[i];
                                // 音階更改註冊 (之後需要演算)
                                switch (i) {
                                    case 0:
                                        pitchs[c] = 5;
                                        break;
                                    case 1:
                                        pitchs[c] = 4;
                                        break;
                                    case 2:
                                        pitchs[c] = 3;
                                        break;
                                    case 3:
                                        pitchs[c] = 2;
                                        break;
                                    case 4:
                                        pitchs[c] = 1;
                                        break;
                                    case 5:
                                    case 6:
                                        pitchs[c] = 0;
                                        break;
                                    case 7:
                                        pitchs[c] = -1;
                                        break;
                                    case 8:
                                        pitchs[c] = -2;
                                        break;
                                    case 9:
                                        pitchs[c] = -3;
                                        break;
                                    case 10:
                                        pitchs[c] = -4;
                                        break;
                                    case 11:
                                        pitchs[c] = -5;
                                        break;
                                }
                            }
                    }
                }





                // ------------------------------------------- 將音階 和 樂器 緩存在數組 -------------------------------------------
                // 取得全部通道
                for (int t = 0; t < tracks.length; ++t) {
                    for (int i = 0, s = tracks[t].size(); i < s; ++i) {

                        long tick = tracks[t].get(i).getTick(); // 取得時間貞

                        List<Integer[]> list = null; // 初始化

                        if (map.containsKey(tick)) {
                            // 已經存在 數組增加一個值
                            list = map.get(tick);

                        } else {
                            // 不存在 初始化數組
                            list = new ArrayList<Integer[]>();

                        }



                        byte[] bytes = tracks[t].get(i).getMessage().getMessage(); // 取得資料
                        int[] ints = new int[bytes.length];

                        // 計算全部數據
                        for (int n = 0; n < bytes.length; ++n)
                            ints[n] = bytes[n] & 0xFF;





                        if (ints[0] >= 192 && ints[0] <= 207) { // 控制代碼 更改樂器
                            sounds[ints[0] - 192] = ints[1]; // 註冊新樂器到指定頻道


                        } else if (ints[0] >= 144 && ints[0] <= 159) { // 控制代碼 聲音開始
                            int c = decryptStatus(tracks[t].get(i).getMessage().getStatus())[1]; // 頻道編號

                            // 如果是頻道 10 則 值 + 128
                            if (c == 9) {
                                list.add(new Integer[]{sounds[c] + 128, ints[1] + (pitchs[c] * 12), ints[2]});
                            } else {
                                list.add(new Integer[]{sounds[c], ints[1] + (pitchs[c] * 12), ints[2]});
                            }
                            map.put(tick, list); // 寫入 map


                        } else if (ints[0] == 255) { // 特殊代碼 檢查下一個值

                            if (ints[1] == 81) { // 控制代碼 速度變換
                                // 計算值
                                int v = bytes[4] & 0xFF |
                                        (bytes[3] & 0xFF) << 8 |
                                        (bytes[2] & 0xFF) << 16;

                                int bpm = 60000000 / v; // 計算BPM

                                list.add(new Integer[]{256, bpm});
                                map.put(tick, list); // 寫入 map


                            }
                        }


                    }
                }

                // 按照順序排序
                Map<Long, List<Integer[]>> audio_track = new TreeMap<Long, List<Integer[]>>(map);






                // ------------------------------------------- 檢查播放速度 -------------------------------------------
                if ( sequence.getDivisionType() == Sequence.PPQ ) {

                } else if ( sequence.getDivisionType() == Sequence.SMPTE_24) {
                    tempo = 40; // 提醒!  無進行詳細測試
                } else if ( sequence.getDivisionType() == Sequence.SMPTE_25 ) {
                    tempo = 50; // 提醒!  無進行詳細測試
                } else if ( sequence.getDivisionType() == Sequence.SMPTE_30 ) {
                    tempo = 60; // 提醒!  無進行詳細測試
                } else if ( sequence.getDivisionType() == Sequence.SMPTE_30DROP ) {
                    tempo = 59; // 提醒!  無進行詳細測試
                }

                //System.out.println(tempo); // 測試用






                // ------------------------------------------- 開始撥放 -------------------------------------------
                run = new BukkitRunnable() {
                    @Override
                    public void run() {

                        // 循環每次需要前進的 tick
                        for (long i = tick ; tick < i + tempo; tick++ ) {


                            // 超過普面最後的 tick
                            if (tick > tick_length) {
                                tick = 0; // 重製播放進度

                                if (end_cache) {
                                    // 清理還存
                                    file = null;
                                } else {
                                    if ( replay ) {
                                        // 重播

                                        continue;
                                    }
                                }

                                runCancel(); // 關閉線程
                                cancel();
                                return;
                            }


                            // 如果數據裡面 並沒有此 tick 的資料
                            if (!audio_track.containsKey(tick)) {
                                //++tick;
                                continue; // 不繼續運行
                            }




                            List<Integer[]> list = audio_track.get(tick); // 取得 map 該貞的數據資料清單




                            // 檢查所有資料
                            for (int c = 0, s = list.size(); c < s; ++c) {

                                // 如果此值不存在
                                if (list.get(c)[1] == -1)
                                    continue; // 不繼續運行






                                Sound sound = null; // 初始化

                                // 檢查類型
                                if (list.get(c)[0] >= 0 &&list.get(c)[0] <= 127 ) {
                                    // 正常樂器
                                    sound = conversionSound(list.get(c)[0]);

                                } else if (list.get(c)[0] >= 128 && list.get(c)[0] <= 255) { // 頻道 10
                                    // 打擊樂器
                                    sound = conversionPercussion(list.get(c)[0]);

                                } else if (list.get(c)[0] == 256) {
                                    // 更改速度
                                    tempo = (int) ( list.get(c)[1] / 5 );
                                    continue;

                                } else {
                                    continue;
                                }




                                // 如果樂器類型不存在
                                if (sound == null)
                                    continue; // 不繼續運行

                                if (list.get(c)[2] == 0) // 音量為 0
                                    continue; // 不繼續運行




                                // 撥放聲音給玩家清單內的所有玩家
                                for(Player player:players) {
                                    Location location = player.getLocation();
                                    // 音量範圍 0 ~ 15 格方塊
                                    float volume = 15 - ( 15 * ( list.get(c)[2] / 127) * volumes );

                                    if (volume > 15)
                                        volume = 15;
                                    if (volume <= 0)
                                        volume = 0;

                                    // 計算位置 確保響應位置在視角正前方 以確保音量保持相同
                                    double radians = Math.toRadians(location.getYaw() + 90); // 弧度
                                    double pitch = Math.toRadians(location.getPitch()); // 仰望高度

                                    // 三角函數計算
                                    location.setX(location.getX() + volume * Math.cos(radians) * Math.cos(pitch));
                                    location.setY(location.getY() + volume * Math.sin(pitch) * -1);
                                    location.setZ(location.getZ() + volume * Math.sin(radians) * Math.cos(pitch));


                                    // 播放聲音
                                    switch (list.get(c)[1]) {
                                        case 54: // f#3
                                            player.playSound(location, sound, 2F, 0.5F);
                                            break;
                                        case 55: // g3
                                            player.playSound(location, sound, 2F, 0.529732F);
                                            break;
                                        case 56: // g#3
                                            player.playSound(location, sound, 2F, 0.561231F);
                                            break;
                                        case 57: // a3
                                            player.playSound(location, sound, 2F, 0.594604F);
                                            break;
                                        case 58: // a#3
                                            player.playSound(location, sound, 2F, 0.629961F);
                                            break;
                                        case 59: // b3
                                            player.playSound(location, sound, 2F, 0.667420F);
                                            break;

                                        // 4
                                        case 60: // c4
                                            player.playSound(location, sound, 2F, 0.707107F);
                                            break;
                                        case 61: // c#4
                                            player.playSound(location, sound, 2F, 0.749154F);
                                            break;
                                        case 62: // d4
                                            player.playSound(location, sound, 2F, 0.7793701F);
                                            break;
                                        case 63: // d#4
                                            player.playSound(location, sound, 2F, 0.840896F);
                                            break;
                                        case 64: // e4
                                            player.playSound(location, sound, 2F, 0.890899F);
                                            break;
                                        case 65: // f4
                                            player.playSound(location, sound, 2F, 0.943874F);
                                            break;
                                        case 66: // f#4
                                            player.playSound(location, sound, 2F, 1F);
                                            break;
                                        case 67: // g4
                                            player.playSound(location, sound, 2F, 1.059463F);
                                            break;
                                        case 68: // g#4
                                            player.playSound(location, sound, 2F, 1.122462F);
                                            break;
                                        case 69: // a4
                                            player.playSound(location, sound, 2F, 1.189207F);
                                            break;
                                        case 70: // a#4
                                            player.playSound(location, sound, 2F, 1.259921F);
                                            break;
                                        case 71: // b4
                                            player.playSound(location, sound, 2F, 1.334840F);
                                            break;

                                        // 5
                                        case 72: // c5
                                            player.playSound(location, sound, 2F, 1.414214F);
                                            break;
                                        case 73: // c#5
                                            player.playSound(location, sound, 2F, 1.498307F);
                                            break;
                                        case 74: // d5
                                            player.playSound(location, sound, 2F, 1.587401F);
                                            break;
                                        case 75: // d#5
                                            player.playSound(location, sound, 2F, 1.681793F);
                                            break;
                                        case 76: // e5
                                            player.playSound(location, sound, 2F, 1.781797F);
                                            break;
                                        case 77: // f5
                                            player.playSound(location, sound, 2F, 1.887749F);
                                            break;
                                        case 78: // f#5
                                            player.playSound(location, sound, 2F, 2F);
                                            break;
                                    }
                                }




                            }
                        }
                    }
                }.runTaskTimerAsynchronously(plugin, 0L, 1L);



                return true; // 返回 成功
            } else {
                tick = 0;
                runCancel(); // 關閉線程

                return false; // 返回 失敗
            }

        } catch (Exception ex) {
            // 出錯
            ex.printStackTrace();
            tick = 0;
            runCancel(); // 關閉線程

            return false; // 返回 失敗
        }
    }

    // 停止播放
    public synchronized void stop() {
        runCancel(); // 關閉線程
    }

    // 重新播放
    public synchronized boolean rnew() {
        termination(); // 終止

        return start();
    }

    // 終止
    public synchronized void termination() {
        tick = 0;
        runCancel(); // 關閉線程
    }












    private synchronized void runCancel() {
        if ( run != null ) {
            run.cancel();
            run = null;
        }
    }

    // 轉換音軌 Status 資料
    private int[] decryptStatus(int status) {
        int command = status & 0xF0; // 指令編號
        int channel = status & 0x0F; // 頻道編號
        return new int[]{command, channel};
    }

    // 轉換樂器
    private synchronized Sound conversionSound(int i) {
        switch (i) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
                // 鋼琴
                return Sound.BLOCK_NOTE_BLOCK_HARP;

            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
                //敲擊樂器
                return Sound.BLOCK_NOTE_BLOCK_BELL;

            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
                // 風琴
                return Sound.BLOCK_NOTE_BLOCK_HARP;

            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
                // 吉他
                return Sound.BLOCK_NOTE_BLOCK_GUITAR;

            case 32:
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
                // 貝斯
                return Sound.BLOCK_NOTE_BLOCK_GUITAR;

            case 40:
            case 41:
            case 42:
            case 43:
            case 44:
            case 45:
            case 46:
            case 47:
                // 弦樂器
                return Sound.BLOCK_NOTE_BLOCK_CHIME;

            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
                // 合奏
                return Sound.BLOCK_NOTE_BLOCK_HARP;
            case 55:
                // 合奏 交響打擊樂
                return Sound.BLOCK_NOTE_BLOCK_BELL;

            case 56:
            case 57:
            case 58:
            case 59:
            case 60:
            case 61:
            case 62:
            case 63:
                // 銅管樂器
                return Sound.BLOCK_NOTE_BLOCK_HARP;

            case 64:
            case 65:
            case 66:
            case 67:
            case 68:
            case 69:
            case 70:
            case 71:
                // 簧樂器
                return Sound.BLOCK_NOTE_BLOCK_BASS;

            case 72:
            case 73:
            case 74:
            case 75:
            case 76:
            case 77:
            case 78:
            case 79:
                // 吹管樂器
                return Sound.BLOCK_NOTE_BLOCK_FLUTE;

            case 80:
            case 81:
                // 合成音主旋律
                return Sound.BLOCK_NOTE_BLOCK_HARP;
            case 82:
                // 合成音主旋律 汽笛風琴
            case 83:
                // 合成音主旋律 合成吹管
                return Sound.BLOCK_NOTE_BLOCK_FLUTE;
            case 84:
                // 合成音主旋律 合成電吉他
                return Sound.BLOCK_NOTE_BLOCK_GUITAR;
            case 85:
                // 合成音主旋律 人聲鍵盤
            case 86:
                // 合成音主旋律 五度音
                return Sound.BLOCK_NOTE_BLOCK_HARP;
            case 87:
                // 合成音主旋律 貝斯吉他合奏
                return Sound.BLOCK_NOTE_BLOCK_GUITAR;

            case 88:
            case 89:
            case 90:
            case 91:
            case 92:
            case 93:
            case 94:
            case 95:
                // 合成音和弦襯底
                return Sound.BLOCK_NOTE_BLOCK_HARP;

            case 96:
            case 97:
            case 98:
            case 99:
            case 100:
            case 101:
            case 102:
            case 103:
                // 合成音效果
                return Sound.BLOCK_NOTE_BLOCK_GUITAR;

            case 104:
            case 105:
            case 106:
            case 107:
            case 108:
            case 109:
            case 110:
            case 111:
                // 民族樂器
                return Sound.BLOCK_NOTE_BLOCK_HARP;

            case 112:
                // 打擊樂器 叮噹鈴
                return Sound.BLOCK_NOTE_BLOCK_BELL;
            case 113:
                // 打擊樂器 阿哥哥鼓
            case 114:
                // 打擊樂器 鋼鼓
            case 115:
                // 打擊樂器 木魚
                return Sound.BLOCK_NOTE_BLOCK_HAT;
            case 116:
                // 打擊樂器 太鼓
            case 117:
                // 打擊樂器 定音筒鼓
            case 118:
                // 打擊樂器 合成鼓
            case 119:
                // 打擊樂器 逆轉鈸聲
                return Sound.BLOCK_NOTE_BLOCK_BASEDRUM;

            // 特殊音效
            case 120:
            case 121:
            case 122:
            case 123:
            case 124:
            case 125:
            case 126:
            case 127:
                return Sound.BLOCK_NOTE_BLOCK_BASS;


            default:
                return null;
        }
    }

    /*
    頻道 10 作為打擊樂器使用的
    */
    // 轉換打擊樂器
    private synchronized Sound conversionPercussion(int i) {
        i = i - 128;
        switch (i){

            case 34:
                // 大鼓2
            case 35:
                // 大鼓1
                return Sound.BLOCK_NOTE_BLOCK_BASEDRUM;
            case 36:
                // 小鼓鼓邊
                return Sound.BLOCK_NOTE_BLOCK_BASEDRUM;
            case 37:
                // 小鼓1
            case 38:
                // 拍手
            case 39:
                // 小鼓2
            case 40:
                // 低音筒鼓2
                return Sound.BLOCK_NOTE_BLOCK_CHIME;
            case 41:
                // 閉合開合鈸
            case 42:
                // 低音筒鼓1
            case 43:
                // 腳踏開合鈸
            case 44:
                // 中音筒鼓2
            case 45:
                // 開放開合鈸
            case 46:
                // 中音筒鼓1
            case 47:
                // 高音筒鼓2
            case 48:
                // 強音鈸1
            case 49:
                // 高音筒鼓1
                return Sound.BLOCK_NOTE_BLOCK_BASEDRUM;
            case 50:
                // 打點鈸1
            case 51:
                // 鈸
            case 52:
                // 響鈴
            case 53:
                // 鈴鼓
                return Sound.BLOCK_NOTE_BLOCK_BELL;
            case 54:
                // 小鈸銅鈸
            case 55:
                // 牛鈴
            case 56:
                // 強音鈸2
            case 57:
                // 噪音器
                return Sound.BLOCK_NOTE_BLOCK_SNARE;
            case 58:
                // 打點鈸2
            case 59:
                // 高音邦加鼓
            case 60:
                // 低音邦加鼓
            case 61:
                // 悶音高音康加鼓
            case 62:
                // 開放高音康加鼓
            case 63:
                // 低音康加鼓
            case 64:
                // 高音天巴雷鼓
            case 65:
                // 低音天巴雷鼓
            case 66:
                // 高音阿哥哥
            case 67:
                // 低音阿哥哥
                return Sound.BLOCK_NOTE_BLOCK_BASEDRUM;
            case 68:
                // 鐵沙鈴
            case 69:
                // 沙槌
                return Sound.BLOCK_NOTE_BLOCK_SNARE;
            case 70:
                // 短口哨
            case 71:
                // 長口哨
            case 72:
                // 短刮瓜
            case 73:
                // 長刮瓜
                return Sound.BLOCK_NOTE_BLOCK_BASEDRUM;
            case 74:
                // 擊木
            case 75:
                // 高音木魚
            case 76:
                // 低音木魚
            case 77:
                //
            case 78:
                //
                return Sound.BLOCK_NOTE_BLOCK_HAT;
            case 79:
                // 悶音三角鐵
            case 80:
                // 開放三角鐵
                return Sound.BLOCK_NOTE_BLOCK_BELL;

            default:
                return null;
        }
    }
}
