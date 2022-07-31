CREATE TABLE IF NOT EXISTS service_mode
  (
     id                             SMALLINT(5) UNSIGNED NOT NULL auto_increment,
     authorize_mode_selection       BIT,
     auto_authorize_limit           INTEGER,
     auto_clear_trans_delay_time    INTEGER,
     auto_unlock_trans_delay_time   INTEGER,
     code                           VARCHAR(255),
     description                    VARCHAR(255),
     enabled                        BIT,
     max_no_consecutive_zero_trans  INTEGER,
     max_nozzle_down_time           INTEGER,
     max_pre_authorize_time         INTEGER,
     min_trans_money                INTEGER,
     min_trans_volume               INTEGER,
     money_trans_buffer_status      BIT,
     pump_light_mode                VARCHAR(255),
     stop_fuellingpoint_vehicle_tag BIT,
     store_pre_authorize            BIT,
     sup_trans_buffer_size          INTEGER,
     unsup_trans_buffer_size        INTEGER,
     use_vehicle_tag_reading_button BIT,
     volume_trans_buffer_status     BIT,
     zero_trans_to_pos              BIT,
     PRIMARY KEY (id),
     UNIQUE KEY service_mode (code)
  )
engine=innodb 