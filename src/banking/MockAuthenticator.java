package banking;

public class MockAuthenticator implements Authentication {

	public AuthenticatedClient authenticateClient(String username, String password) throws AuthenticationException {
		if(username == null) throw new AuthenticationException("username is null");
		if(password == null) throw new AuthenticationException("password is null");
    	for(Client c : Client.getClients()) {
			if(username.equals(c.getUsername()) && password.equals(c.getPassword())) {
				return new AuthenticatedClient(c.getClientId(),AuthenticationMethod.USERNAME);
			}
		}
		throw new AuthenticationException("Username and/or password incorrect");
    }
    public AuthenticatedClient authenticateCard(String cardId, String pin) throws AuthenticationException {
		if(cardId == null) throw new AuthenticationException("cardId is null");
		if(pin == null) throw new AuthenticationException("ping is null");
    	for(Client c : Client.getClients()) {
			if(cardId.equals(c.getCardId()) && pin.equals(c.getPin())) {
				return new AuthenticatedClient(c.getClientId(),AuthenticationMethod.CARD);
			}
		}
		throw new AuthenticationException("Card and/or pin incorrect");
    }
}

