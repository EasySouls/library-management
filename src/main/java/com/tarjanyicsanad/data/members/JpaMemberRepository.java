package com.tarjanyicsanad.data.members;

import com.tarjanyicsanad.data.members.entities.MemberEntity;
import com.tarjanyicsanad.domain.exceptions.MemberNotFoundException;
import com.tarjanyicsanad.domain.model.Member;
import com.tarjanyicsanad.domain.repository.BaseRepository;
import com.tarjanyicsanad.domain.repository.MemberRepository;
import jakarta.persistence.EntityManager;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Optional;

public class JpaMemberRepository
        extends BaseRepository<MemberEntity, Integer, MemberNotFoundException>
        implements MemberRepository {
    @Inject
    public JpaMemberRepository(EntityManager entityManager) {
        super(entityManager, MemberEntity.class);
    }

    @Override
    public void addMember(Member member) {
        super.save(member.toEntity());
    }

    @Override
    public Optional<Member> getMember(int id) {
        return Optional.ofNullable(super.findById(id)).map(Member::fromEntity);
    }

    @Override
    public ArrayList<Member> findAllMembers() {
        ArrayList<Member> members = new ArrayList<>();
        super.findAll().forEach(memberEntity -> members.add(Member.fromEntity(memberEntity)));
        return members;
    }

    @Override
    public void removeMember(int id) throws MemberNotFoundException {
        MemberEntity memberEntity = super.findById(id);
        if (memberEntity == null) {
            throw new MemberNotFoundException("Member with " + id + " not found");
        }
        super.delete(memberEntity);
    }

    @Override
    public void updateMember(Member member) {
        super.update(member.toEntity());
    }
}
