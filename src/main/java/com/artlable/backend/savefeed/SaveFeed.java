package com.artlable.backend.savefeed;

import com.artlable.backend.common.AuditingFields;
import com.artlable.backend.feed.command.domain.aggregate.entity.Feed;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "saveFeed")
@Getter
@ToString
public class SaveFeed extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "save_feed_no")
    private Long saveFeedNo;

    @ManyToOne
    @JoinColumn
    private Feed feedNo;

    private boolean saveFeedIsDeleted;

    public SaveFeed() {

    }

    public SaveFeed(Long saveFeedNo, Feed feedNo, boolean saveFeedIsDeleted) {
        this.saveFeedNo = saveFeedNo;
        this.feedNo = feedNo;
        this.saveFeedIsDeleted = saveFeedIsDeleted;
    }

    public void setSaveFeedNo(Long saveFeedNo) {
        this.saveFeedNo = saveFeedNo;
    }

    public void setFeedNo(Feed feedNo) {
        this.feedNo = feedNo;
    }

    public void setSaveFeedIsDeleted(boolean saveFeedIsDeleted) {
        this.saveFeedIsDeleted = saveFeedIsDeleted;
    }
}
