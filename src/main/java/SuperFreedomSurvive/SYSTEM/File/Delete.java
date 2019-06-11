package SuperFreedomSurvive.SYSTEM.File;

import java.io.File;

final public class Delete {
    static final public void DeleteAll(File path) {
        try {
            if (!path.exists()) {
                return;
            }
            if (path.isFile()) {
                for ( ; path.exists() ; ) {
                    if (path.delete()) {
                        return;
                    }
                    Thread.currentThread().sleep(1000);
                }
                return;
            }
            File[] files = path.listFiles();
            for (int i = 0, s = files.length; i < s; i++) {
                DeleteAll(files[i]);
            }
            path.delete();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
