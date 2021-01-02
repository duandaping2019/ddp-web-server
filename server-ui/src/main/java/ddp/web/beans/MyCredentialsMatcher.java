package ddp.web.beans;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;

public class MyCredentialsMatcher extends HashedCredentialsMatcher {

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        Object tokenHashedCredentials = hashProvidedCredentials(token, info);//这里将得到页面传递来的password通过加密后的结果
        Object accountCredentials = getCredentials(info);//这里得到是数据库的passwrod通过加密后的结果
        return equals(tokenHashedCredentials, accountCredentials);
    }

}
