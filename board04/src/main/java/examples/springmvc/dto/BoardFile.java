package examples.springmvc.dto;

public class BoardFile {
    private Long id;
    private Long boardId;
    private String fileName;
    private String saveFileName;
    private String mimeType;
    private int fileLength;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBoardId() {
        return boardId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setBoardId(Long boardId) {
        this.boardId = boardId;
    }

    public String getSaveFileName() {
        return saveFileName;
    }

    public void setSaveFileName(String saveFileName) {
        this.saveFileName = saveFileName;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public int getFileLength() {
        return fileLength;
    }

    public void setFileLength(int fileLength) {
        this.fileLength = fileLength;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BoardFile boardFile = (BoardFile) o;

        return id != null ? id.equals(boardFile.id) : boardFile.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "BoardFile{" +
                "id=" + id +
                ", boardId=" + boardId +
                ", fileName='" + fileName + '\'' +
                ", saveFileName='" + saveFileName + '\'' +
                ", mimeType='" + mimeType + '\'' +
                ", fileLength=" + fileLength +
                '}';
    }
}

/*
    id INTEGER auto_increment PRIMARY KEY,
    board_id INTEGER,
    file_name VARCHAR(255),
    save_file_name VARCHAR(255),
    mime_type VARCHAR(255),
    file_length INTEGER
 */