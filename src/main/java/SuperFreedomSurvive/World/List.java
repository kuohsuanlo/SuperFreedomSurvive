package SuperFreedomSurvive.World;

import java.util.ArrayList;

final public class List {
    // 創建2維陣列組
    static public ArrayList<String> world_register = new ArrayList<String>();

    static final public boolean Have(String name) {
        // 是否有此世界在註冊表中
        for (int i = 0 ; i < world_register.size() ; ++i) {
            if (world_register.get(i).equals(name)) {
                // 有
                return true;
            }
        }
        // 沒有
        return false;
    }

    // 加入到註冊表
    static final public boolean Add(String name) {
        // 增加到註冊表中
        if (Have(name)) {
            // 已經有了
            return false;
        } else {
            // 加入
            world_register.add(name);
            return true;
        }
    }

    // 從註冊表中刪除
    static final public boolean Remove(String name) {
        boolean yes = false;
        for (int i = 0 ; i < world_register.size() ; ++i) {
            if (world_register.get(i).equals(name)) {
                // 有
                world_register.remove(i);
                yes = true;
            }
        }
        // 沒有
        return yes;
    }

    // 取得全部
    static final public ArrayList Get() {
        return world_register;
    }

    // 清除
    static final public void Remake() {
        world_register = new ArrayList<String>();
    }
}
