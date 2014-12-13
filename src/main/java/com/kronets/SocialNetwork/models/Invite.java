package com.kronets.SocialNetwork.models;

import javax.persistence.*;

/**
 * @author Volodymyr Grynda
 */
@Entity
@Table(name = "Invites")
public class Invite {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "invite_id")
    private long inviteId;

    @Column(name = "Invite")
    private String invite;

    public Invite() {

    }

    public Invite(String invite) {
        this.invite = invite;
    }

    public long getInviteId() {
        return inviteId;
    }

    public void setInviteId(long inviteId) {
        this.inviteId = inviteId;
    }

    public String getInvite() {
        return invite;
    }

    public void setInvite(String invite) {
        this.invite = invite;
    }
}
