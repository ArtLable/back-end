package com.artlable.backend.feed.command.domain.aggregate.entity;

import com.artlable.backend.common.AuditingFields;

import javax.persistence.*;

@Entity
@Table(name = "saveFeed")
public class SaveFeed extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "save_feed_no")
    private Long saveFeedNo;

    @ManyToOne
    @JoinColumn
    private FeedEntity feedNo;

    private boolean saveFeedIsDeleted;
}
