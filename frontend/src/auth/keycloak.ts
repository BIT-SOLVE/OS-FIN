import Keycloak from 'keycloak-js';

const keycloakConfig = {
  url: import.meta.env.VITE_KEYCLOAK_URL || 'http://localhost:8081',
  realm: import.meta.env.VITE_KEYCLOAK_REALM || 'ufos',
  clientId: import.meta.env.VITE_KEYCLOAK_CLIENT_ID || 'ufos-web',
};

const keycloak = new Keycloak(keycloakConfig);

export const initKeycloak = (onAuthenticatedCallback: () => void) => {
  keycloak.init({
    onLoad: 'check-sso',
    silentCheckSsoRedirectUri: window.location.origin + '/silent-check-sso.html',
    pkceMethod: 'S256',
  })
    .then((authenticated) => {
      if (authenticated) {
        console.log("User is authenticated");
      } else {
        console.log("User is not authenticated");
      }
      onAuthenticatedCallback();
    })
    .catch((err) => {
      console.error("Keycloak init failed", err);
      onAuthenticatedCallback();
    });
};

export const doLogin = () => keycloak.login();
export const doLogout = () => keycloak.logout();
export const getToken = () => keycloak.token;
export const getParsedToken = () => keycloak.tokenParsed;
export const updateToken = (successCallback: () => void) => {
  keycloak.updateToken(5)
    .then(successCallback)
    .catch(doLogin);
};
export const getUsername = () => keycloak.tokenParsed?.preferred_username;
export const hasRole = (roles: string[]) => roles.some((role) => keycloak.hasRealmRole(role));
export const isAuthenticated = () => !!keycloak.authenticated;

export default keycloak;
