package ecoo.convert;

import ecoo.data.User;
import ecoo.ws.security.json.PassportUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Justin Rundle
 *@since April 2017
 */
public class UserDetailsToPassportUserConverter implements Converter<UserDetails, PassportUser> {

    @Override
    public PassportUser convert(UserDetails userDetails) {
        Assert.notNull(userDetails);

        final Set<String> roles = new HashSet<>();
        if (userDetails.getAuthorities() != null) {
            roles.addAll(userDetails.getAuthorities().stream()
                    .filter(grantedAuthority -> StringUtils.isNotBlank(grantedAuthority.getAuthority()))
                    .map((Function<GrantedAuthority, String>) GrantedAuthority::getAuthority)
                    .collect(Collectors.toList()));
        }
        return new PassportUser((User) userDetails, roles);
    }
}
