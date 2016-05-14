package bliareg.organizer.model;

import javax.persistence.*;

/**
 * Created by bliareg on 10.12.15.
 */

@Entity @Table(name = "notes")
public class Note {

    @Id @Column @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column private long userid;
    @Column private String note;
    @Column(name = "createtime") private String createTime;
    @Column(name = "istodo") private boolean isTodo;
    @Column(name = "remindpattern") private String remindPattern;
    @Column private String header;

    public Note(long userid, String note, String createTime, boolean isTodo, String remindPattern, String header) {
        this.userid = userid;
        this.note = note;
        this.createTime = createTime;
        this.isTodo = isTodo;
        this.remindPattern = remindPattern;
        this.header = header;
    }

    protected Note() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserid() {
        return userid;
    }

    public void setUserid(long userid) {
        this.userid = userid;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public boolean isTodo() {
        return isTodo;
    }

    public void setTodo(boolean todo) {
        isTodo = todo;
    }

    public String getReminderPattern() {
        return remindPattern;
    }

    public void setReminderPattern(String reminderPattern) {
        this.remindPattern = reminderPattern;
    }

    public String getRemindPattern() {
        return remindPattern;
    }

    public void setRemindPattern(String remindPattern) {
        this.remindPattern = remindPattern;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", userid=" + userid +
                ", note='" + note + '\'' +
                ", createTime='" + createTime + '\'' +
                ", isTodo=" + isTodo +
                ", remindPattern='" + remindPattern + '\'' +
                ", header='" + header + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Note note = (Note) o;

        if (id != note.id) return false;
        return userid == note.userid;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (userid ^ (userid >>> 32));
        return result;
    }
}
