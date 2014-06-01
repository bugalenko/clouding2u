import com.google.gson.JsonObject;
public abstract class AbstractAccessControlPoliciesFabric {
	public enum AccessPermissionsType{
		readFromCollection("read"),
		updateCollection("update"),
		deleteFromCollection("delete"),
		administrator("admin");
		private AccessPermissionsType(String accessCredentialName) {
			// TODO Auto-generated constructor stub
			this.accessCredentialName=accessCredentialName;
		}
		private String accessCredentialName;
		public String getAccessCredentialName(){
			return accessCredentialName;
		}
	};
	abstract int addUsergroup(AccessPermissionsType accessPermissions,EnginioUsergroup usergroup);
	abstract int addUser(AccessPermissionsType accessPermissions,User user);
	abstract JsonObject makeRules();
	abstract JsonObject makeSinglePermission(AccessPermissionsType permissionsType);
}
