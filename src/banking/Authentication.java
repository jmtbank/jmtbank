package banking;

public interface Authentication {
    public AuthenticatedClient authenticateClient(String username, String password) throws AuthenticationException;
    public AuthenticatedClient authenticateCard(String cardId, String pin) throws AuthenticationException;
}
