package com.kongling.platform.entity.VO.RolePermission;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MenuMetaVO implements Serializable {

    private String title;
    private String icon;
}
