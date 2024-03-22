package org.mrbonk97.fileshareserver.dto.fileData.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@RequiredArgsConstructor
public class UploadFileRequest {
    private final MultipartFile file;
}
