package banking;

import bank.authentication.AuthenticationException;

public interface Authentication {
    public AuthenticatedClient authenticateClient(String username, String password) throws AuthenticationException;
    public AuthenticatedClient authenticateCard(String cardId, String pin) throws AuthenticationException;
}
