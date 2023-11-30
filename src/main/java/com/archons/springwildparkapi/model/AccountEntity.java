package com.archons.springwildparkapi.model;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tblaccount")
@JsonIgnoreProperties({ "ownedOrganizations", "adminOrganizations", "memberOrganizations", "vehicles", "bookings",
        "payments", "role", "enabled", "authorities", "username", "accountNonLocked", "accountNonExpired",
        "credentialsNonExpired", "admin" })
public class AccountEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    @Column(name = "email")
    public String email;

    @Column(name = "password")
    public String password;

    @Column(name = "firstname")
    public String firstname;

    @Column(name = "lastname")
    public String lastname;

    @Column(name = "birthdate")
    public LocalDateTime birthdate;

    @Column(name = "is_admin")
    public boolean isAdmin;

    @Column(name = "contact_no")
    private String contactNo;

    @Column(name = "gender")
    private String gender;

    @Column(name = "street")
    private String street;

    @Column(name = "municipality")
    private String municipality;

    @Column(name = "province")
    private String province;

    @Column(name = "country")
    private String country;

    @Column(name = "zip_code")
    private int zipCode;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "owner")
    private List<VehicleEntity> vehicles;

    @OneToMany(mappedBy = "owner")
    private List<OrganizationEntity> ownedOrganizations;

    @ManyToMany(mappedBy = "admins")
    private List<OrganizationEntity> adminOrganizations;

    @ManyToMany(mappedBy = "members")
    private List<OrganizationEntity> memberOrganizations;

    @OneToMany(mappedBy = "booker")
    private List<BookingEntity> bookings;

    @OneToMany(mappedBy = "payor")
    private List<PaymentEntity> payments;

    @Column(name = "is_enabled")
    private boolean enabled;

    public AccountEntity() {
    }

    public AccountEntity(int id, String email, String password, String firstname, String lastname,
            LocalDateTime birthdate, boolean isAdmin, String contactNo, String gender, String street,
            String municipality, String province, String country, int zipCode, Role role, List<VehicleEntity> vehicles,
            List<OrganizationEntity> ownedOrganizations, List<OrganizationEntity> adminorganizations,
            List<OrganizationEntity> memberOrganizations, List<BookingEntity> bookings,
            List<PaymentEntity> payments, boolean enabled) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthdate = birthdate;
        this.isAdmin = isAdmin;
        this.contactNo = contactNo;
        this.gender = gender;
        this.street = street;
        this.municipality = municipality;
        this.province = province;
        this.country = country;
        this.zipCode = zipCode;
        this.role = role;
        this.vehicles = vehicles;
        this.ownedOrganizations = ownedOrganizations;
        this.adminOrganizations = adminorganizations;
        this.memberOrganizations = memberOrganizations;
        this.bookings = bookings;
        this.payments = payments;
        this.enabled = enabled;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public LocalDateTime getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDateTime birthdate) {
        this.birthdate = birthdate;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getMunicipality() {
        return municipality;
    }

    public void setMunicipality(String municipality) {
        this.municipality = municipality;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<VehicleEntity> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<VehicleEntity> vehicles) {
        this.vehicles = vehicles;
    }

    public List<OrganizationEntity> getOwnedOrganizations() {
        return ownedOrganizations;
    }

    public void setOwnedOrganizations(List<OrganizationEntity> ownedOrganizations) {
        this.ownedOrganizations = ownedOrganizations;
    }

    public List<OrganizationEntity> getAdminOrganizations() {
        return adminOrganizations;
    }

    public void setAdminOrganizations(List<OrganizationEntity> adminOrganizations) {
        this.adminOrganizations = adminOrganizations;
    }

    public List<OrganizationEntity> getMemberOrganizations() {
        return memberOrganizations;
    }

    public void setMemberOrganizations(List<OrganizationEntity> memberOrganizations) {
        this.memberOrganizations = memberOrganizations;
    }

    public List<BookingEntity> getBookings() {
        return bookings;
    }

    public void setBookings(List<BookingEntity> bookings) {
        this.bookings = bookings;
    }

    public List<PaymentEntity> getPayments() {
        return payments;
    }

    public void gsetPayments(List<PaymentEntity> payments) {
        this.payments = payments;
    }

    public boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result + ((password == null) ? 0 : password.hashCode());
        result = prime * result + ((firstname == null) ? 0 : firstname.hashCode());
        result = prime * result + ((lastname == null) ? 0 : lastname.hashCode());
        result = prime * result + ((birthdate == null) ? 0 : birthdate.hashCode());
        result = prime * result + (isAdmin ? 1231 : 1237);
        result = prime * result + ((contactNo == null) ? 0 : contactNo.hashCode());
        result = prime * result + ((gender == null) ? 0 : gender.hashCode());
        result = prime * result + ((street == null) ? 0 : street.hashCode());
        result = prime * result + ((municipality == null) ? 0 : municipality.hashCode());
        result = prime * result + ((province == null) ? 0 : province.hashCode());
        result = prime * result + ((country == null) ? 0 : country.hashCode());
        result = prime * result + zipCode;
        result = prime * result + ((role == null) ? 0 : role.hashCode());
        result = prime * result + ((vehicles == null) ? 0 : vehicles.hashCode());
        result = prime * result + ((ownedOrganizations == null) ? 0 : ownedOrganizations.hashCode());
        result = prime * result + ((adminOrganizations == null) ? 0 : adminOrganizations.hashCode());
        result = prime * result + ((memberOrganizations == null) ? 0 : memberOrganizations.hashCode());
        result = prime * result + ((bookings == null) ? 0 : bookings.hashCode());
        result = prime * result + ((payments == null) ? 0 : payments.hashCode());
        result = prime * result + (enabled ? 1231 : 1237);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        AccountEntity other = (AccountEntity) obj;
        if (id != other.id)
            return false;
        if (email == null) {
            if (other.email != null)
                return false;
        } else if (!email.equals(other.email))
            return false;
        if (password == null) {
            if (other.password != null)
                return false;
        } else if (!password.equals(other.password))
            return false;
        if (firstname == null) {
            if (other.firstname != null)
                return false;
        } else if (!firstname.equals(other.firstname))
            return false;
        if (lastname == null) {
            if (other.lastname != null)
                return false;
        } else if (!lastname.equals(other.lastname))
            return false;
        if (birthdate == null) {
            if (other.birthdate != null)
                return false;
        } else if (!birthdate.equals(other.birthdate))
            return false;
        if (isAdmin != other.isAdmin)
            return false;
        if (contactNo == null) {
            if (other.contactNo != null)
                return false;
        } else if (!contactNo.equals(other.contactNo))
            return false;
        if (gender == null) {
            if (other.gender != null)
                return false;
        } else if (!gender.equals(other.gender))
            return false;
        if (street == null) {
            if (other.street != null)
                return false;
        } else if (!street.equals(other.street))
            return false;
        if (municipality == null) {
            if (other.municipality != null)
                return false;
        } else if (!municipality.equals(other.municipality))
            return false;
        if (province == null) {
            if (other.province != null)
                return false;
        } else if (!province.equals(other.province))
            return false;
        if (country == null) {
            if (other.country != null)
                return false;
        } else if (!country.equals(other.country))
            return false;
        if (zipCode != other.zipCode)
            return false;
        if (role != other.role)
            return false;
        if (vehicles == null) {
            if (other.vehicles != null)
                return false;
        } else if (!vehicles.equals(other.vehicles))
            return false;
        if (ownedOrganizations == null) {
            if (other.ownedOrganizations != null)
                return false;
        } else if (!ownedOrganizations.equals(other.ownedOrganizations))
            return false;
        if (adminOrganizations == null) {
            if (other.adminOrganizations != null)
                return false;
        } else if (!adminOrganizations.equals(other.adminOrganizations))
            return false;
        if (memberOrganizations == null) {
            if (other.memberOrganizations != null)
                return false;
        } else if (!memberOrganizations.equals(other.memberOrganizations))
            return false;
        if (bookings == null) {
            if (other.bookings != null)
                return false;
        } else if (!bookings.equals(other.bookings))
            return false;
        if (payments == null) {
            if (other.payments != null)
                return false;
        } else if (!payments.equals(other.payments))
            return false;
        if (enabled != other.enabled)
            return false;
        return true;
    }
}
