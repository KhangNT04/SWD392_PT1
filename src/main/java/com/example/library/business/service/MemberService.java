package com.example.library.business.service;

import com.example.library.business.exception.ResourceNotFoundException;
import com.example.library.data.entity.Member;
import com.example.library.data.entity.UserAccount;
import com.example.library.data.enums.AccountStatus;
import com.example.library.data.enums.UserRole;
import com.example.library.data.repository.BorrowingRepository;
import com.example.library.data.repository.MemberRepository;
import com.example.library.web.dto.BorrowingRecordDTO;
import com.example.library.web.dto.MemberDTO;
import com.example.library.web.dto.MemberRequestDTO;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final BorrowingRepository borrowingRepository;

    public MemberService(MemberRepository memberRepository, BorrowingRepository borrowingRepository) {
        this.memberRepository = memberRepository;
        this.borrowingRepository = borrowingRepository;
    }

    @Transactional(readOnly = true)
    public MemberDTO getMemberById(Long memberId) {
        return toDto(getMemberEntity(memberId));
    }

    @Transactional(readOnly = true)
    public List<MemberDTO> getAllMembers() {
        return memberRepository.findAll().stream().map(this::toDto).toList();
    }

    @Transactional
    public MemberDTO createMember(MemberRequestDTO requestDTO) {
        Member member = new Member();
        applyRequest(member, requestDTO);
        member.setMembershipDate(LocalDate.now());
        member.setStatus(AccountStatus.ACTIVE);
        member.setUserAccount(createTemplateUserAccount(requestDTO));
        return toDto(memberRepository.save(member));
    }

    @Transactional
    public MemberDTO updateMember(Long memberId, MemberRequestDTO requestDTO) {
        Member member = getMemberEntity(memberId);
        applyRequest(member, requestDTO);
        if (member.getUserAccount() != null) {
            member.getUserAccount().setUsername(requestDTO.getUsername());
            member.getUserAccount().setEmail(requestDTO.getEmail());
        }
        return toDto(memberRepository.save(member));
    }

    @Transactional
    public void deactivateMember(Long memberId) {
        Member member = getMemberEntity(memberId);
        member.setStatus(AccountStatus.INACTIVE);
        if (member.getUserAccount() != null) {
            member.getUserAccount().setStatus(AccountStatus.INACTIVE);
        }
        memberRepository.save(member);
    }

    @Transactional(readOnly = true)
    public List<BorrowingRecordDTO> getBorrowingHistory(Long memberId) {
        getMemberEntity(memberId);
        return borrowingRepository.findByMember_MemberId(memberId).stream()
                .map(BorrowingMapper::toDto)
                .toList();
    }

    private Member getMemberEntity(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found with id: " + memberId));
    }

    private void applyRequest(Member member, MemberRequestDTO requestDTO) {
        member.setFullName(requestDTO.getFullName());
        member.setPhone(requestDTO.getPhone());
        member.setAddress(requestDTO.getAddress());
    }

    private UserAccount createTemplateUserAccount(MemberRequestDTO requestDTO) {
        UserAccount account = new UserAccount();
        account.setUsername(requestDTO.getUsername());
        account.setEmail(requestDTO.getEmail());
        account.setPasswordHash("TODO_SET_PASSWORD_HASH");
        account.setRole(UserRole.MEMBER);
        account.setStatus(AccountStatus.ACTIVE);
        return account;
    }

    private MemberDTO toDto(Member member) {
        return new MemberDTO(
                member.getMemberId(),
                member.getFullName(),
                member.getUserAccount() != null ? member.getUserAccount().getEmail() : null,
                member.getPhone(),
                member.getAddress(),
                member.getStatus());
    }
}
