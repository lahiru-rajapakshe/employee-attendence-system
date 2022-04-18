package security;

public class SecurityContextHolder {

    private static Principal principal;

    public static void clear(){
        principal = null;
    }

    public static Principal getPrincipal() {
        return principal;
    }

    public static void setPrincipal(Principal principal) {
        SecurityContextHolder.principal = principal;
    }
}
