package com.artlable.backend.file.command.domain.aggregate.entity;

import com.artlable.backend.common.AuditingFields;
import com.artlable.backend.feed.command.domain.aggregate.entity.FeedEntity;
import com.artlable.backend.member.command.domain.aggregate.entity.MemberEntity;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "file")
@Getter
@ToString
public class FileEntity extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_no")
    private Long fileNo;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_path")
    private String filePath;

    @ManyToOne
    @JoinColumn(name = "member_no")
    private MemberEntity memberNo;

    @ManyToOne
    @JoinColumn(name = "feed_No")
    private FeedEntity feedNo;
}
