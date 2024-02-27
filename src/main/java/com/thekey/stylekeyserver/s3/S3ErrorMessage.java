package com.thekey.stylekeyserver.s3;

public enum S3ErrorMessage {
    FILE_UPLOAD_FAILED("이미지 파일 업로드에 실패했습니다."),
    FILE_ALREADY_EXISTS("동일한 이름의 이미지 파일이 존재합니다."),
    FAIL_IMAGE_DELETE("이미지 삭제에 실패했습니다."),
    ;
    private String msg;

    S3ErrorMessage(String msg) {
        this.msg = msg;
    }

    public String get() {
        return msg;
    }
}
