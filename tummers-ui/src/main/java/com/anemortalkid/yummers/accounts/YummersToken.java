package com.anemortalkid.yummers.accounts;

import java.util.UUID;

public class YummersToken {

	private UUID tokenId;

	public UUID getTokenId() {
		return tokenId;
	}

	private AccountCredentials credentials;

	public YummersToken(UUID tokenId, AccountCredentials credentials) {
		super();
		this.tokenId = tokenId;
		this.credentials = credentials;
	}

	public AccountCredentials getCredentials() {
		return credentials;
	}

}
