package com.tarjanyicsanad.ui.members;

import com.tarjanyicsanad.domain.repository.MemberRepository;

import javax.swing.*;

public class MembersScreen extends JPanel {
    private MemberRepository memberRepository;

    public MembersScreen(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
}
