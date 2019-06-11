package SuperFreedomSurvive.SYSTEM.File;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

final public class Copy {
    static final public void CopyAll(String oldPath, String newPath) {

        try {
            (new File(newPath)).mkdirs();
            //如果文件夹不存在 则建立新文件夹
            File a = new File(oldPath);
            String[] file = a.list();
            File temp = null;
            for (int i = 0, s = file.length; i < s; i++) {
                if (oldPath.endsWith(File.separator)) {
                    temp = new File(oldPath + file[i]);
                } else {
                    temp = new File(oldPath + File.separator + file[i]);
                }

                if (temp.isFile()) {
                    FileInputStream input = new FileInputStream(temp);
                    FileOutputStream output = new FileOutputStream(newPath + "/"
                            + (temp.getName()));
                    byte[] b = new byte[1024 * 5];
                    int len;
                    while ((len = input.read(b)) != -1) {
                        output.write(b, 0, len);
                    }
                    output.flush();
                    output.close();
                    input.close();
                }
                if (temp.isDirectory()) {//如果是子文件夹
                    CopyAll(oldPath + "/" + file[i], newPath + "/" + file[i]);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
