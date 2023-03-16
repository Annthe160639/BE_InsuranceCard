package com.swp.g3.entity;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonSetter(nulls = Nulls.SKIP)
    private Integer id = null;
    @NotEmpty(message = "Thiếu tên người dùng")

    private String username;
    @NotEmpty(message = "Thiếu mật khẩu")
    @JsonSetter(nulls = Nulls.SKIP)
    private String password;
    private String name;
    @NotEmpty(message = "Thiếu số điện thoại")
    private String phone;
    @Email(message = "Email không hợp lệ")
    @NotEmpty(message = "Thiếu gmail")
    private String gmail;
    private String address;
    @NotEmpty(message = "Thiếu số căn cước công dân")
    private String ci;
    @JsonSetter(nulls = Nulls.SKIP)
    private boolean isActive = false;
    private String role = "customer";
    private Integer managerId;

    public Customer(){}

    public Customer(String username, String password){
        this.username = username;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCi() {
        return ci;
    }

    public void setCi(String ci) {
        this.ci = ci;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getManagerId() {
        return managerId;
    }

    public void setManagerId(Integer managerId) {
        this.managerId = managerId;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", gmail='" + gmail + '\'' +
                ", address='" + address + '\'' +
                ", ci='" + ci + '\'' +
                ", isActive=" + isActive +
                ", role='" + role + '\'' +
                ", managerId=" + managerId +
                '}';
    }
}
