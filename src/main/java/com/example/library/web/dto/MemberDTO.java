package com.example.library.web.dto;

import com.example.library.data.enums.AccountStatus;

public class MemberDTO {

    private Long memberId;
    private String fullName;
    private String email;
    private String phone;
    private String address;
    private AccountStatus status;

    public MemberDTO() {
    }

    public MemberDTO(Long memberId, String fullName, String email, String phone, String address, AccountStatus status) {
        this.memberId = memberId;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.status = status;
    }

    public Long getMemberId() { return memberId; }
    public void setMemberId(Long memberId) { this.memberId = memberId; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public AccountStatus getStatus() { return status; }
    public void setStatus(AccountStatus status) { this.status = status; }
}
