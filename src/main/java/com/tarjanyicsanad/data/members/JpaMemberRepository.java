package com.tarjanyicsanad.data.members;

import com.tarjanyicsanad.data.members.entities.MemberEntity;
import com.tarjanyicsanad.domain.exceptions.MemberNotFoundException;
import com.tarjanyicsanad.domain.model.Member;
import com.tarjanyicsanad.domain.repository.BaseRepository;
import com.tarjanyicsanad.domain.repository.MemberRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * JPA implementation of the {@link MemberRepository} interface.
 */
public class JpaMemberRepository
        extends BaseRepository<MemberEntity, Integer, MemberNotFoundException>
        implements MemberRepository {

    /**
     * Creates a new {@link JpaMemberRepository}.
     * @param entityManager the JPA entity manager
     */
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
    public Member findMemberByEmail(String email) throws MemberNotFoundException {
        try {
            MemberEntity entity = entityManager.createQuery(
                            "SELECT a FROM members a WHERE a.email = :email", MemberEntity.class)
                    .setParameter("email", email)
                    .getSingleResult();
            return Member.fromEntity(entity);
        } catch (NoResultException e) {
            throw new MemberNotFoundException("Member with email " + email + " not found");
        }
    }

    @Override
    public List<Member> findMembersWithActiveLoans() {
        List<Member> members = new ArrayList<>();
        entityManager.createQuery(
                        "SELECT DISTINCT m FROM members m JOIN m.loans l WHERE l.returnDate IS NOT NULL AND l.returnDate > :now",
                        MemberEntity.class)
                .setParameter("now", LocalDate.now())
                .getResultList()
                .forEach(memberEntity -> members.add(Member.fromEntity(memberEntity)));
        return members;
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
