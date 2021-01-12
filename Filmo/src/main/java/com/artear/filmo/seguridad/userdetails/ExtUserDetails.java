package com.artear.filmo.seguridad.userdetails;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.naming.Name;

import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.ldap.userdetails.LdapUserDetailsImpl;
import org.springframework.util.Assert;


public class ExtUserDetails extends LdapUserDetailsImpl {
	// ~ Instance fields
	// ================================================================================================
	private static final long serialVersionUID = 5520563376999130537L;
	private String dn;
	private String password;
	private String username;
	private Integer idEmpresa;
	private String empresa;
	private List<String> roles;
	private List<String> generadores;
	
	private List<GrantedAuthority> authorities = AuthorityUtils.NO_AUTHORITIES;
	private Map<String, Object> attributes = new HashMap<String, Object>();

	private boolean accountNonExpired = true;
	private boolean accountNonLocked = true;
	private boolean credentialsNonExpired = true;
	private boolean enabled = true;
	// PPolicy data
	private int timeBeforeExpiration = Integer.MAX_VALUE;
	private int graceLoginsRemaining = Integer.MAX_VALUE;
	
	private List<String> senal;

	// ~ Constructors
	// ===================================================================================================

	protected ExtUserDetails() {
	}

	// ~ Methods
	// ========================================================================================================
	public Map<String, Object> getAttributes() {
		return attributes;
	}

	@Override
	public int hashCode() {
		return dn.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ExtUserDetails other = (ExtUserDetails) obj;
		if (dn == null) {
			if (other.dn != null)
				return false;
		} else if (!dn.equals(other.dn))
			return false;
		return true;
	}

	public Object getAttribute(String key) {

		return attributes.get(key);
	}

	public List<GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public List<String> getRoles() {
		return roles;
	}

	public List<String> getGeneradores() {
		return generadores;
	}
	
	public String getDn() {
		return dn;
	}

	public String getPassword() {
		return password;
	}

	public String getUsername() {
		return username;
	}
	
	public Integer getIdEmpresa() {
		return idEmpresa;
	}
	
	public String getEmpresa() {
		return empresa;
	}

	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public int getTimeBeforeExpiration() {
		return timeBeforeExpiration;
	}

	public int getGraceLoginsRemaining() {
		return graceLoginsRemaining;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(super.toString()).append(": ");
		sb.append("Username: ").append(this.username).append("; ");
		sb.append("Password: [PROTECTED]; ");
		sb.append("IdEmpresa: ").append(this.idEmpresa).append("; ");
		sb.append("Empresa: ").append(this.empresa).append("; ");
		sb.append("Enabled: ").append(this.enabled).append("; ");
		sb.append("AccountNonExpired: ").append(this.accountNonExpired).append("; ");
		sb.append("credentialsNonExpired: ").append(this.credentialsNonExpired).append("; ");
		sb.append("AccountNonLocked: ").append(this.accountNonLocked).append("; ");

		if (this.getAuthorities() != null) {
			sb.append("Granted Authorities: ");

			for (int i = 0; i < this.getAuthorities().size(); i++) {
				if (i > 0) {
					sb.append(", ");
				}

				sb.append(this.getAuthorities().get(i).toString());
			}
		} else {
			sb.append("Not granted any authorities");
		}
		
		String _seniales = new String();
		if(null != this.getSenal())
			for (Iterator<String> iterator = this.getSenal().iterator(); iterator.hasNext();) {
				_seniales += iterator.next();
				
			}
		sb.append("Seniales: ").append( _seniales ).append("; ");
		return sb.toString();
	}

	// ~ Inner Classes
	// ==================================================================================================

	/**
	 * Variation of essence pattern. Used to create mutable intermediate object
	 */
	public static class Essence {
		protected ExtUserDetails instance = createTarget();
		private List<GrantedAuthority> mutableAuthorities = new ArrayList<GrantedAuthority>();
		private Map<String, Object> mutableAttributes = new HashMap<String, Object>();

		public Essence() {
		}

		public Essence(DirContextOperations ctx) {
			setDn(ctx.getDn());
		}

		public Essence(ExtUserDetails copyMe) {
			setDn(copyMe.getDn());
			setUsername(copyMe.getUsername());
			setRoles(copyMe.getRoles());
			setPassword(copyMe.getPassword());
			setIdEmpresa(copyMe.getIdEmpresa());
			setEmpresa(copyMe.getEmpresa());
			setEnabled(copyMe.isEnabled());
			setAccountNonExpired(copyMe.isAccountNonExpired());
			setCredentialsNonExpired(copyMe.isCredentialsNonExpired());
			setAccountNonLocked(copyMe.isAccountNonLocked());
			setAuthorities(copyMe.getAuthorities());
			setAttributes(copyMe.getAttributes());
			setSenal(copyMe.getSenal());
		}

		protected ExtUserDetails createTarget() {
			return new ExtUserDetails();
		}

		/**
		 * Adds the authority to the list, unless it is already there, in which
		 * case it is ignored
		 */
		public void addAttribute(String key, Object value) {
			mutableAttributes.put(key, value);
		}

		
		/**
		 * Adds the authority to the list, unless it is already there, in which
		 * case it is ignored
		 */
		public void addAuthority(GrantedAuthority a) {
			if (!hasAuthority(a)) {
				mutableAuthorities.add(a);
			}
		}

		private boolean hasAuthority(GrantedAuthority a) {
			for (GrantedAuthority authority : mutableAuthorities) {
				if (authority.equals(a)) {
					return true;
				}
			}
			return false;
		}

		public ExtUserDetails createUserDetails() {
			Assert.notNull(instance,
					"Essence can only be used to create a single instance");
			Assert.notNull(instance.username, "username must not be null");
			Assert.notNull(instance.getDn(),
					"Distinguished name must not be null");

			instance.authorities = getGrantedAuthorities();
			instance.attributes = mutableAttributes;
			
			ExtUserDetails newInstance = instance;

			instance = null;

			return newInstance;
		}

		public List<GrantedAuthority> getGrantedAuthorities() {
			return mutableAuthorities;
		}

		public void setAccountNonExpired(boolean accountNonExpired) {
			instance.accountNonExpired = accountNonExpired;
		}

		public void setAccountNonLocked(boolean accountNonLocked) {
			instance.accountNonLocked = accountNonLocked;
		}

		public void setAuthorities(List<GrantedAuthority> authorities) {
			mutableAuthorities = authorities;
		}

		public void setAttributes(Map<String, Object> attributes) {
			mutableAttributes = attributes;
		}

		public void setCredentialsNonExpired(boolean credentialsNonExpired) {
			instance.credentialsNonExpired = credentialsNonExpired;
		}

		public void setDn(String dn) {
			instance.dn = dn;
		}

		public void setDn(Name dn) {
			instance.dn = dn.toString();
		}

		public void setEnabled(boolean enabled) {
			instance.enabled = enabled;
		}

		public void setPassword(String password) {
			instance.password = password;
		}

		public void setUsername(String username) {
			instance.username = username;
		}
		
		public void setIdEmpresa(Integer idEmpresa) {
			instance.idEmpresa = idEmpresa;
		}
		
		public void setEmpresa(String empresa) {
			instance.empresa = empresa;
		}
		
		public void setRoles(List<String> roles) {
			instance.roles = roles;
		}

		public void setGeneradores(List<String> generadores) {
			instance.generadores = generadores;
		}
		
		public void setTimeBeforeExpiration(int timeBeforeExpiration) {
			instance.timeBeforeExpiration = timeBeforeExpiration;
		}

		public void setGraceLoginsRemaining(int graceLoginsRemaining) {
			instance.graceLoginsRemaining = graceLoginsRemaining;
		}
		
		public void setSenal(List<String> _hsenial) {
			instance.senal = _hsenial;
		}
	}

	public void setIdEmpresa(Integer idEmpresa) {
		this.idEmpresa = idEmpresa;
	}
	
	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	/**
	 * @return the hsenial
	 */
	public List<String> getSenal() {
		return senal;
	}

	/**
	 * @param hsenial the hsenial to set
	 */
	public void setSenal(List<String> hsenial) {
		this.senal = hsenial;
	}
}
