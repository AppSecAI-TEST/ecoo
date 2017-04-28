package com.sqlserver.ssrs.reportingservice2005.util;

import java.net.PasswordAuthentication;

/**
 * Created by Justin on 2017-01-30.
 */
public final class ReportAuthenticator extends java.net.Authenticator {

    private final String username;
    private final String password;

    public ReportAuthenticator(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Called when password authorization is needed.  Subclasses should
     * override the default implementation, which returns null.
     *
     * @return The PasswordAuthentication collected from the
     * user, or null if none is provided.
     */
    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(username, password.toCharArray());
    }
}