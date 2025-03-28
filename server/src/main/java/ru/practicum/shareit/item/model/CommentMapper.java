package ru.practicum.shareit.item.model;


import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

public class CommentMapper {
    public static Comment toComment(long authorId, long itemId, CommentDto dto) {
        return new Comment(
                dto.getId(),
                dto.getText(),
                Item.builder().id(itemId).build(),
                User.builder().id(authorId).build(),
                LocalDateTime.now());
    }

    public static CommentDto toCommentDto(Comment comment) {
        return new CommentDto(
                comment.getId(),
                comment.getText(),
                ItemMapper.toItemDto(comment.getItem()),
                comment.getAuthor().getName(),
                comment.getCreated());

    }
}
