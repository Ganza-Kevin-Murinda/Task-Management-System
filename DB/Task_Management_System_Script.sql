CREATE TABLE "user"(
    "id" SERIAL NOT NULL,
    "username" VARCHAR(255) NOT NULL,
    "full_name" VARCHAR(255) NOT NULL,
    "email" VARCHAR(255) NOT NULL,
    "department" VARCHAR(255) NOT NULL,
    "join_date" DATE NOT NULL
);
ALTER TABLE
    "user" ADD PRIMARY KEY("id");
ALTER TABLE
    "user" ADD CONSTRAINT "user_username_unique" UNIQUE("username");
ALTER TABLE
    "user" ADD CONSTRAINT "user_email_unique" UNIQUE("email");
CREATE TABLE "category"(
    "id" SERIAL NOT NULL,
    "name" VARCHAR(255) NOT NULL,
    "description" TEXT NOT NULL,
    "color" VARCHAR(255) NOT NULL
);
ALTER TABLE
    "category" ADD PRIMARY KEY("id");
ALTER TABLE
    "category" ADD CONSTRAINT "category_name_unique" UNIQUE("name");
CREATE TABLE "task"(
    "id" SERIAL NOT NULL,
    "title" VARCHAR(255) NOT NULL,
    "description" TEXT NOT NULL,
    "due_date" TIMESTAMP(0) WITHOUT TIME ZONE NOT NULL,
    "status" VARCHAR(255) NOT NULL DEFAULT 'PENDING',
    "priority" VARCHAR(255) NOT NULL DEFAULT 'MEDIUM',
    "creation_date" TIMESTAMP(0) WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "last_updated_date" TIMESTAMP(0) WITHOUT TIME ZONE NULL,
    "user_id" SERIAL NOT NULL,
    "category_id" SERIAL NOT NULL
);
ALTER TABLE
    "task" ADD PRIMARY KEY("id");
ALTER TABLE
    "task" ADD CONSTRAINT "task_user_id_foreign" FOREIGN KEY("user_id") REFERENCES "user"("id");
ALTER TABLE
    "task" ADD CONSTRAINT "task_category_id_foreign" FOREIGN KEY("category_id") REFERENCES "category"("id");

-- Indexes
CREATE INDEX idx_tasks_user ON task(user_id);
CREATE INDEX idx_tasks_category ON task(category_id);
CREATE INDEX idx_tasks_status ON task(status);
CREATE INDEX idx_tasks_due_date ON task(due_date);