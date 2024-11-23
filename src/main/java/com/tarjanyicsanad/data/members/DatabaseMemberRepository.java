package com.tarjanyicsanad.data.members;

import com.tarjanyicsanad.data.members.entities.MemberEntity;
import com.tarjanyicsanad.domain.model.Member;
import com.tarjanyicsanad.domain.repository.MemberRepository;
import org.hibernate.SessionFactory;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public class DatabaseMemberRepository implements MemberRepository {

    private final SessionFactory sessionFactory;

    @Inject
    public DatabaseMemberRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void addMember(Member member) {
        sessionFactory.inTransaction(session -> {
            MemberEntity entity = member.toEntity();
            session.persist(entity);
        });
    }

    @Override
    public void removeMember(int id) {
        sessionFactory.inTransaction(session -> {
            MemberEntity entity = session.find(MemberEntity.class, id);
            session.remove(entity);
        });
    }

    @Override
    public Optional<Member> getMember(int id) {
        AtomicReference<MemberEntity> memberEntity = new AtomicReference<>();
        sessionFactory.inTransaction(session ->
                memberEntity.set(session.find(MemberEntity.class, id))
        );
        return Optional.ofNullable(memberEntity.get()).map(Member::fromEntity);
    }

    @Override
    public List<Member> findAllMembers() {
        List<MemberEntity> memberEntities = new ArrayList<>();
        sessionFactory.inTransaction(session ->
                memberEntities.addAll(session.createQuery("SELECT m FROM members m", MemberEntity.class).getResultList())
        );
        return memberEntities.stream().map(Member::fromEntity).toList();
    }

    @Override
    public void updateMember(Member member) {
        sessionFactory.inTransaction(session -> {
            MemberEntity entity = member.toEntity();
            session.merge(entity);
        });
    }
}
