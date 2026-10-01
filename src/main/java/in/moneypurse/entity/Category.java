package in.moneypurse.entity;

import in.moneypurse.entity.enums.CategoryType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Index;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "categories", indexes = { @Index(name = "idx_category_type", columnList = "type"),
		@Index(name = "idx_category_parent_id", columnList = "parent_id") })
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category extends BaseEntity {

	@Column(name = "code", nullable = false, unique = true, length = 32)
	private String code;

	@Column(name = "name", nullable = false, length = 100)
	private String name;

	@Column(name = "description", length = 255)
	private String description;

	@Enumerated(EnumType.STRING)
	@Column(name = "type", nullable = false, length = 20)
	private CategoryType type;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id", foreignKey = @ForeignKey(name = "fk_category_parent"))
	private Category parent;

	@Builder.Default
	@Column(name = "is_system", nullable = false)
	private boolean isSystem = false;

	@Builder.Default
	@Column(name = "is_active", nullable = false)
	private boolean isActive = true;

	public static Category create(String name, String description, CategoryType type, boolean isSystem,
			boolean isActive) {
		return Category.builder().name(name).description(description).type(type).isSystem(isSystem).isActive(isActive)
				.build();
	}

	public void updateDetails(String name, String code, String description, CategoryType type, boolean isActive) {
		this.name = name;
		this.code = code;
		this.description = description;
		this.type = type;
		this.isActive = isActive;
	}

	public void setParent(Category parent) {
		this.parent = parent;
	}

	public void setCode(CategoryCode code) {
		this.code = (code != null) ? code.value() : null;
	}
}
