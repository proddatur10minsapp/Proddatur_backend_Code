package com.org.proddaturiMinApp.model;

import com.org.proddaturiMinApp.dto.CartDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "users")
public class User {
    @Id

    private String id;
    @NonNull
    private String phoneNumber;
    private String userName;
    private Map<String, String> address;

    private List<CartDTO> cart = new ArrayList<>();

}
